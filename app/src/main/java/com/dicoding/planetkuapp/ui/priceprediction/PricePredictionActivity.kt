package com.dicoding.planetkuapp.ui.priceprediction

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.planetkuapp.databinding.ActivityPricePredictionBinding
import com.dicoding.planetkuapp.api.startLocalApiServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

class PricePredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPricePredictionBinding
    private val wasteTypes = listOf("Plastik", "Kertas", "Logam", "Kaca", "Organik")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPricePredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Jalankan server API lokal
        startLocalApiServer()

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

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = URL(url).openConnection().apply {
                    doOutput = true
                    setRequestProperty("Content-Type", "application/json")
                    outputStream.write(requestBody.toByteArray())
                }.getInputStream().bufferedReader().readText()

                val prediction = Json.decodeFromString<PredictionResponse>(response)

                withContext(Dispatchers.Main) {
                    binding.tvPredictionResult.text =
                        "Estimasi harga untuk ${prediction.item} adalah Rp ${prediction.predicted_price}"
                    binding.tvPredictionResult.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.tvPredictionResult.text = "Gagal memprediksi harga: ${e.message}"
                    binding.tvPredictionResult.visibility = View.VISIBLE
                }
            }
        }
    }
}

@Serializable
data class PredictionResponse(val item: String, val predicted_price: Double)
