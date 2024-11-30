package kiet.imam.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("https://powerplaybackend-7mfm.onrender.com/route/temp-data")
    suspend fun fetchElectricityData(): List<ApiResponse>
}

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://powerplaybackend-7mfm.onrender.com/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ApiService = retrofit.create(ApiService::class.java)
}

