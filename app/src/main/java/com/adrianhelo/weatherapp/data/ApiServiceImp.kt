package com.adrianhelo.weatherapp.data

import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {
    suspend fun getWeather(lat: Double, lon: Double, units: String, lang: String, apiKey: String) = apiService.getFetchWeather(lat, lon, units, lang, apiKey)
}