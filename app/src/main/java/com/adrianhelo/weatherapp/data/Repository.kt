package com.adrianhelo.weatherapp.data

import retrofit2.http.Query
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getFetchWeather(lat: Double, lon: Double, units: String, lang: String, apiKey: String) = apiService.getFetchWeather(lat, lon, units, lang, apiKey)
}