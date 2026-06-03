package com.adrianhelo.weatherapp.data

import javax.inject.Inject

class ApiService @Inject constructor(private val apiServiceImp: ApiServiceImp) {
    suspend fun getWeather(lat: Double, lon: Double, units: String, lang: String, apiKey: String) = apiServiceImp.getFetchWeather(lat, lon, units, lang, apiKey)
}