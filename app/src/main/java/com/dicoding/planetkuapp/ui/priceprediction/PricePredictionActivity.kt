package com.dicoding.planetkuapp.ui.priceprediction

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.planetkuapp.databinding.ActivityPricePredictionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException

class PricePredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPricePredictionBinding
    private val wasteTypes = listOf("Plastik", "Kertas", "Logam", "Kaca", "Organik")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPricePredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val classifiedWaste = intent.getStringExtra("CLASSIFIED_WASTE")
        setupSpinner(classifiedWaste)

        binding.btnPredictPrice.setOnClickListener {
            val selectedWasteType = binding.spWasteType.selectedItem.toString()
            predictPrice(selectedWasteType)
        }
    }

    private fun setupSpinner(defaultWasteType: String?) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wasteTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spWasteType.adapter = adapter

        defaultWasteType?.let {
            val position = wasteTypes.indexOf(it)
            if (position >= 0) {
                binding.spWasteType.setSelection(position)
            }
        }
    }

    private fun predictPrice(wasteType: String) {
        val url = "http://127.0.0.1:8080/predict"
        val requestBody = """{ "item": "$wasteType" }"""
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                showError("Kesalahan jaringan: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val result = response.body()?.string()
                    val prediction = Json.decodeFromString<PredictionResponse>(result!!)
                    showPrediction(prediction)
                } else {
                    showError("Gagal memprediksi harga")
                }
            }
        })
    }

    private fun showPrediction(prediction: PredictionResponse) {
        runOnUiThread {
            binding.tvPredictionResult.text =
                "Estimasi harga untuk ${prediction.item} adalah Rp ${prediction.predicted_price}"
            binding.tvPredictionResult.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Serializable
data class PredictionResponse(val item: String, val predicted_price: Double)
