package com.dicoding.planetkuapp.ui.priceprediction

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.planetkuapp.databinding.ActivityPricePredictionBinding

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
            calculatePricePrediction(selectedWasteType)
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

    private fun calculatePricePrediction(wasteType: String) {
        // Dummy logic prediksi harga
        val pricePrediction = when (wasteType) {
            "Plastik" -> "Rp 10.000/kg"
            "Kertas" -> "Rp 5.000/kg"
            "Logam" -> "Rp 20.000/kg"
            "Kaca" -> "Rp 7.500/kg"
            "Organik" -> "Rp 1.500/kg"
            else -> "Tidak diketahui"
        }

        binding.tvPredictionResult.text = "Estimasi harga untuk $wasteType adalah $pricePrediction"
        binding.tvPredictionResult.visibility = View.VISIBLE
    }
}

