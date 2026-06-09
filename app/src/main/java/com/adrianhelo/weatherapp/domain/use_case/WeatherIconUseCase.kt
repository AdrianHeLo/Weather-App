package com.adrianhelo.weatherapp.domain.use_case

import com.adrianhelo.weatherapp.data.ApiService
import com.adrianhelo.weatherapp.presentation.util.WeatherIcons.getWeatherIcon
import javax.inject.Inject

class WeatherIconUseCase @Inject constructor(private val apiService: ApiService) {
    // Construimos la URL del icono (ejemplo: "04d" -> url de OpenWeather)
    suspend operator fun invoke(iconCode: String?): Int {
        return getWeatherIcon(iconCode)
    }
}