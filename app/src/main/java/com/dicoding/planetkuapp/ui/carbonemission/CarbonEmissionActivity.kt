package com.dicoding.planetkuapp.ui.carbonemission

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.planetkuapp.databinding.ActivityCarbonEmissionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

// Data class untuk respon API
data class CarbonCategory(
    val electriccity: String,
    val gas: String,
    val transportation: String,
    val food: String,
    val organic_waste: String,
    val inorganic_waste: String,
    val carbon_footprint: String
)

// Interface API
interface CarbonEmissionApi {
    @GET("/carbon-emission")
    suspend fun getCarbonCategories(@Query("user_id") userId: String): List<CarbonCategory>

    @POST("/carbon-emission")
    suspend fun addCarbonEmission(@Body emissionData: CarbonCategory): Unit

    @PUT("/carbon-emission")
    suspend fun updateCarbonEmission(@Query("user_id") userId: String, @Body emissionData: CarbonCategory): Unit
}

class CarbonEmissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarbonEmissionBinding

    private val carbonCategoryAdapter by lazy {
        CarbonCategoryAdapter { category ->
            navigateToCategoryDetail(category)
        }
    }

    // Retrofit API client
    private val api: CarbonEmissionApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://main-api-83958552057.asia-southeast2.run.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarbonEmissionApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarbonEmissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchCarbonCategories("1")  // Contoh user_id

        binding.btnAddActivity.setOnClickListener {
            showAddActivityDialog()
        }
    }

    private fun setupRecyclerView() {
        binding.rvCarbonCategories.apply {
            adapter = carbonCategoryAdapter
            layoutManager = LinearLayoutManager(this@CarbonEmissionActivity)
        }
    }

    private fun fetchCarbonCategories(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getCarbonCategories(userId)
                withContext(Dispatchers.Main) {
                    carbonCategoryAdapter.submitList(response)
                    Toast.makeText(this@CarbonEmissionActivity, "Data berhasil dimuat!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CarbonEmissionActivity, "Gagal memuat data: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun navigateToCategoryDetail(category: CarbonCategory) {
        Toast.makeText(this, "Navigasi ke detail data emisi", Toast.LENGTH_SHORT).show()
        // Tambahkan logika navigasi ke detail di sini
    }

    private fun showAddActivityDialog() {
        Toast.makeText(this, "Tambah Aktivitas Baru", Toast.LENGTH_SHORT).show()
        // Tambahkan logika untuk menampilkan dialog tambah aktivitas di sini
    }
}
