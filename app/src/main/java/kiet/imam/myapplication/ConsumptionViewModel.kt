package kiet.imam.myapplication


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ConsumptionViewModel  : ViewModel() {
    private val _data = mutableStateOf<List<ApiResponse>>(emptyList())
    val data: State<List<ApiResponse>> = _data

    init {
        fetchElectricityData()
    }

    private fun fetchElectricityData() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.fetchElectricityData()
                _data.value = response
                Log.d("API Response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }
    fun refreshData() {
        viewModelScope.launch {
            try {
                val response = ApiClient.service.fetchElectricityData()
                _data.value = response
                Log.d("API Response", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }
}

