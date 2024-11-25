package com.dicoding.planetkuapp.ui.priceprediction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.FragmentPricePredictionBinding

class PricePredictionFragment : Fragment(R.layout.fragment_price_prediction) {

    private var _binding: FragmentPricePredictionBinding? = null
    private val binding get() = _binding!!

    private val wastePriceMap = mapOf(
        "Plastik" to 2000.0,
        "Kertas" to 1000.0,
        "Kaca" to 1500.0,
        "Logam" to 5000.0,
        "Organik" to 500.0
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPricePredictionBinding.bind(view)

        setupWasteTypeSpinner()

        binding.btnPredictPrice.setOnClickListener {
            calculateEstimatedPrice()
        }
    }

    private fun setupWasteTypeSpinner() {
        val wasteTypes = wastePriceMap.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, wasteTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spWasteType.adapter = adapter
    }

    private fun calculateEstimatedPrice() {
        val selectedWasteType = binding.spWasteType.selectedItem?.toString()
        val weightText = binding.etWeight.text.toString()

        if (selectedWasteType.isNullOrEmpty() || weightText.isEmpty()) {
            Toast.makeText(requireContext(), "Mohon isi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        val weight = weightText.toDoubleOrNull()
        if (weight == null || weight <= 0) {
            Toast.makeText(requireContext(), "Mohon masukkan berat yang valid", Toast.LENGTH_SHORT).show()
            return
        }

        val pricePerKg = wastePriceMap[selectedWasteType] ?: 0.0
        val estimatedPrice = weight * pricePerKg

        binding.tvPredictionResult.apply {
            text = "Estimasi harga untuk $selectedWasteType seberat $weight kg adalah Rp${estimatedPrice.toInt()}"
            visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
