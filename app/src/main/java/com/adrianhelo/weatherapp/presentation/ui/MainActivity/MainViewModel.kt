package com.adrianhelo.weatherapp.presentation.ui.MainActivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianhelo.weatherapp.data.UserPreference
import com.adrianhelo.weatherapp.data.ApiService
import com.adrianhelo.weatherapp.domain.UserSettings
import com.adrianhelo.weatherapp.domain.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val preferenceManager: UserPreference, private val apiService: ApiService): ViewModel() {

    // Definimos un estado privado y uno público para proteger la mutabilidad
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    // Coordenadas internas
    private val _coords = MutableStateFlow<Pair<Double, Double>?>(null)

    // Función para que la Activity pase las coordenadas una sola vez
    fun setCoordinates(lat: Double, lon: Double){
        if (lat != 0.0 && lon != 0.0){
            _coords.value = Pair(lat, lon)
        }
    }

    // Convertimos el Flow de DataStore en un State que Compose pueda entender
    val userSettingsState: StateFlow<UserSettings?> = combine(
        _coords.filterNotNull(), preferenceManager.settingsFlow){ coords, prefs ->
        UserSettings(coords.first, coords.second, prefs.first, prefs.second)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun toggleUnits() {
        viewModelScope.launch {
            val current = userSettingsState.value?.units ?: "metric"
            val next = if (current == "metric") "imperial" else "metric"
            preferenceManager.saveUnits(next)
        }
    }

    fun toggleLanguage(){
        viewModelScope.launch {
            val currentLanguage = userSettingsState.value?.lang ?: "en"
            val next = if (currentLanguage == "en") "sp" else "en"
            preferenceManager.saveLanguage(next)
        }
    }

    // Lógica centralizada: Observa cambios en coordenadas Y unidades
    init {
        viewModelScope.launch {
            userSettingsState.collect { settings ->
                settings?.let {
                    getWeather(it)
                }
            }
        }
    }

    private fun getWeather(userSettings: UserSettings){

        val API_KEY = "9717f5a7213d360cd6eee358d6f89617"

        Log.d("API_DEBUG", "Enviando a Repo -> Key: '$API_KEY'")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getWeather(userSettings.lat, userSettings.lon, userSettings.units, userSettings.lang, API_KEY)
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