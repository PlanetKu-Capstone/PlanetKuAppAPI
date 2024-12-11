package com.dicoding.planetkuapp.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Data response dari API
data class LocalCarbonEmissionResponse(
    val predicted_carbon_footprint: Float
)

// Data request untuk API
data class LocalCarbonEmissionRequest(
    val electricity: Float,
    val gas: Float,
    val transportation: Float,
    val food: Float,
    val organic_waste: Float,
    val inorganic_waste: Float
)

// Interface API Service
interface LocalApiServer {
    @POST("/predict")
    fun predictCarbonEmission(
        @Body request: CarbonEmissionRequest
    ): Call<CarbonEmissionResponse>
}
