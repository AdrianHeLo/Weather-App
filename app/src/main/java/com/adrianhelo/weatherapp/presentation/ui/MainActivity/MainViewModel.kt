package com.adrianhelo.weatherapp.presentation.ui.MainActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianhelo.weather.R
import com.adrianhelo.weatherapp.data.UnitsPreference
import com.adrianhelo.weatherapp.data.ApiService
import com.adrianhelo.weatherapp.data.LanguagePreference
import com.adrianhelo.weatherapp.domain.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val preferenceManager: UnitsPreference, private val languagePreference: LanguagePreference, private val apiService: ApiService): ViewModel() {

    // Definimos un estado privado y uno público para proteger la mutabilidad
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    // Convertimos el Flow de DataStore en un State que Compose pueda entender
    val unitsState = preferenceManager.unitsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "metric" // Valor inicial mientras carga
    )

    // Convertimos el Flow de DataStore en un State que Compose pueda entender para el LanguagePreference
    val languageState = languagePreference.languageFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "en"
    )

    // Coordenadas internas
    private val _coords = MutableStateFlow<Pair<Double, Double>?>(null)

    // Función para que la Activity pase las coordenadas una sola vez
    fun setCoordinates(lat: Double, lon: Double){
        if (lat != 0.0 && lon != 0.0){
            _coords.value = Pair(lat, lon)
        }
    }

    fun toggleUnits() {
        viewModelScope.launch {
            val current = unitsState.value
            val next = if (current == "metric") "imperial" else "metric"
            preferenceManager.saveUnits(next)
        }
    }

    fun toggleLanguage(){
        viewModelScope.launch {
            val currentLanguage = languageState.value
            val next = if (currentLanguage == "en") "sp" else "en"
            languagePreference.saveLanguage(next)
        }
    }

    // Lógica centralizada: Observa cambios en coordenadas Y unidades
    init {
        viewModelScope.launch {
            combine(_coords.filterNotNull(), unitsState, languageState){ coords, units, lang ->
                Triple(coords, units, lang)
            }.collect { (coords, units, lang) ->
                getWeather(coords.first, coords.second, units, lang)
            }
        }
    }

    private fun getWeather(lat: Double, lon: Double, units: String, lang: String){

        val apiKey = "9717f5a7213d360cd6eee358d6f89617"

        Log.d("API_DEBUG", "Enviando a Repo -> Key: '$apiKey'")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getWeather(lat, lon, units, lang, apiKey)
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