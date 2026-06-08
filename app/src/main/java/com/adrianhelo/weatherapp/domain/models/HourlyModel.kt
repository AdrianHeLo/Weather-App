package com.adrianhelo.weatherapp.domain.models

data class HourlyModel(
    val hour: String,
    val temp: Int,
    val picPath: String
)
