package com.dicoding.planetkuapp.ui.carbonemission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.planetkuapp.R
import com.dicoding.planetkuapp.databinding.FragmentCarbonEmissionBinding

class CarbonEmissionFragment : Fragment(R.layout.fragment_carbon_emission) {

    private var _binding: FragmentCarbonEmissionBinding? = null
    private val binding get() = _binding!!

    private val carbonCategoryAdapter by lazy {
        CarbonCategoryAdapter { category ->
            navigateToCategoryDetail(category)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCarbonEmissionBinding.bind(view)

        setupRecyclerView()

        binding.btnAddActivity.setOnClickListener {
            showAddActivityDialog()
        }

        displayCarbonCategories()
    }

    private fun setupRecyclerView() {
        binding.rvCarbonCategories.apply {
            adapter = carbonCategoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun displayCarbonCategories() {
        val categories = listOf(
            CarbonCategory("Electricity", "120 kg CO2"),
            CarbonCategory("Gas", "80 kg CO2"),
            CarbonCategory("Transportation", "150 kg CO2"),
            CarbonCategory("Food", "60 kg CO2"),
            CarbonCategory("Organic Waste", "30 kg CO2"),
            CarbonCategory("Inorganic Waste", "40 kg CO2")
        )
        carbonCategoryAdapter.submitList(categories)
    }

    private fun navigateToCategoryDetail(category: CarbonCategory) {
        Toast.makeText(requireContext(), "Navigasi ke ${category.categoryName}", Toast.LENGTH_SHORT).show()
    }

    private fun showAddActivityDialog() {
        Toast.makeText(requireContext(), "Tambah Aktivitas Baru", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
