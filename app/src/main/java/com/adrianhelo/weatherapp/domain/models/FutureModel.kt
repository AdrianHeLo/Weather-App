package com.adrianhelo.weatherapp.domain.models

data class FutureModel(
    val day: String,
    val picPath: String,
    val status: String,
    val highTemp: Int,
    val lowTemp: Int
)
