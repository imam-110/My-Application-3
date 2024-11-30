package kiet.imam.myapplication

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val hour_of_day: Int,
    val electricity_demand: Float,
    val label : String

)
