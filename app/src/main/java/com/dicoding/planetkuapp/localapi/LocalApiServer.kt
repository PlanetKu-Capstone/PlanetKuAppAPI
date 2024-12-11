package com.dicoding.planetkuapp.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import joblib.Joblib

@Serializable
data class PredictionRequest(val item: String)

@Serializable
data class PredictionResponse(val item: String, val predicted_price: Double)

fun startLocalApiServer(port: Int = 8080) {
    embeddedServer(Netty, host = "127.0.0.1", port = port) {
        install(io.ktor.features.ContentNegotiation) {
            json(Json { prettyPrint = true })
        }
        routing {
            post("/predict") {
                try {
                    val request = call.receive<PredictionRequest>()

                    // Load model dan encoder
                    val model = Joblib.load(File("linear_model.pkl"))
                    val labelEncoder = Joblib.load(File("label_encoder.pkl"))
                    val features = Joblib.load<List<String>>(File("features.pkl"))

                    // Encode item
                    val itemEncoded = try {
                        labelEncoder.transform(arrayOf(request.item))[0]
                    } catch (e: Exception) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            mapOf("error" to "Item '${request.item}' tidak dikenal.")
                        )
                        return@post
                    }

                    // Buat DataFrame input
                    val inputData = mutableMapOf<String, Int>().apply {
                        features.forEach { this[it] = 0 }
                        this["item_${request.item}"] = 1
                    }

                    // Prediksi harga
                    val predictedPrice = model.predict(inputData)[0]

                    call.respond(
                        PredictionResponse(
                            item = request.item,
                            predicted_price = predictedPrice
                        )
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
                }
            }
        }
    }.start(wait = true)
}
