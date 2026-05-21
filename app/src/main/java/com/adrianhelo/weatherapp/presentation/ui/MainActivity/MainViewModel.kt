package com.adrianhelo.weatherapp.presentation.ui.MainActivity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adrianhelo.weatherapp.data.UnitsPreferenceImp
import com.adrianhelo.weatherapp.data.ApiServiceImp
import com.adrianhelo.weatherapp.domain.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val preferenceManager: UnitsPreferenceImp, private val apiServiceImp: ApiServiceImp): ViewModel() {

    // Definimos un estado privado y uno público para proteger la mutabilidad
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    // Convertimos el Flow de DataStore en un State que Compose pueda entender
    val unitsState = preferenceManager.unitsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "metric" // Valor inicial mientras carga
    )

    fun toggleUnits() {
        viewModelScope.launch {
            val current = unitsState.value
            val next = if (current == "metric") "imperial" else "metric"
            preferenceManager.saveUnits(next)
        }
    }

    fun getWeather(lat: Double, lon: Double, units: String, lang: String, apiKey: String){
        Log.d("API_DEBUG", "Enviando a Repo -> Key: '$apiKey'")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServiceImp.getWeather(lat, lon, units, lang, apiKey)
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