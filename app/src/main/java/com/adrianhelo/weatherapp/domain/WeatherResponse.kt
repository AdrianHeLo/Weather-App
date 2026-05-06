package com.adrianhelo.weatherapp.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("weather")
    @Expose
    val weather: List<Weather>,

    @SerializedName("main")
    @Expose
    val main: Main,

    @SerializedName("wind")
    @Expose
    val wind: Wind
)
