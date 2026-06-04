package com.adrianhelo.weatherapp.domain

data class UserSettings(
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val units: String = "metric",
    val lang: String = "en"
)
