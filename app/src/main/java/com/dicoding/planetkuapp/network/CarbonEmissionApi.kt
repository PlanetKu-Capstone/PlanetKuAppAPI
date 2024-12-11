package com.dicoding.planetkuapp.network

// Data untuk Prediksi Emisi Karbon
data class CarbonEmissionRequest(
    val electricity: Double,
    val gas: Double,
    val transportation: Double,
    val food: Double,
    val organic_waste: Double,
    val inorganic_waste: Double
)

data class CarbonEmissionResponse(
    val predicted_carbon_footprint: Double
)

// Data untuk Data Karbon Umum
data class CarbonData(
    val category: String,
    val amount: Float,
    val date: String
)
