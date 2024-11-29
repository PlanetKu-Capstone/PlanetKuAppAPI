package com.dicoding.planetkuapp.ui.carbonemission

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.planetkuapp.databinding.ActivityCarbonEmissionBinding

class CarbonEmissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarbonEmissionBinding

    private val carbonCategoryAdapter by lazy {
        CarbonCategoryAdapter { category ->
            navigateToCategoryDetail(category)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarbonEmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnAddActivity.setOnClickListener {
            showAddActivityDialog()
        }

        displayCarbonCategories()
    }

    private fun setupRecyclerView() {
        binding.rvCarbonCategories.apply {
            adapter = carbonCategoryAdapter
            layoutManager = LinearLayoutManager(this@CarbonEmissionActivity)
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
        Toast.makeText(this, "Navigasi ke ${category.categoryName}", Toast.LENGTH_SHORT).show()
        // Tambahkan logika navigasi ke detail di sini
    }

    private fun showAddActivityDialog() {
        Toast.makeText(this, "Tambah Aktivitas Baru", Toast.LENGTH_SHORT).show()
        // Tambahkan logika untuk menampilkan dialog tambah aktivitas di sini
    }
}

