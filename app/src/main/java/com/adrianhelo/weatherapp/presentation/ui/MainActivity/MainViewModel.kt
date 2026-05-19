package com.adrianhelo.weatherapp.presentation.ui.MainActivity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianhelo.weatherapp.data.Repository
import com.adrianhelo.weatherapp.domain.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    // Definimos un estado privado y uno público para proteger la mutabilidad
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    // "metric" por defecto
    var unitSelection by mutableStateOf("metric")
        private set

    fun toggleUnits() {
        unitSelection = if (unitSelection == "metric") "imperial" else "metric"
        //fetchWeather() // Volvemos a llamar a la API con la nueva unidad
    }

    suspend fun getWeather(lat: Double, lon: Double, units: String, lang: String, apiKey: String){
        Log.d("API_DEBUG", "Enviando a Repo -> Key: '$apiKey'")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getFetchWeather(lat, lon, units, lang, apiKey)
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        _weatherData.value = body
                        Log.i("API_DEBUG", "City: ${body.name}")
                    }else{
                        Log.e("API_DEBUG", "Respuesta exitosa pero body nulo")
                    }
                }else{
                    Log.e("API_DEBUG", "Error: ${response.code()}")
                }
            }catch (e:Exception){
                Log.e("API_DEBUG", "Error: ${e.message}")
            }
        }
    }

}