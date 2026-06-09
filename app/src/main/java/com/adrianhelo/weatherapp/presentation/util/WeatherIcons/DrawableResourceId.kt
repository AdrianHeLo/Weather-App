package com.adrianhelo.weatherapp.presentation.util.WeatherIcons

import androidx.compose.runtime.Composable
import com.adrianhelo.weather.R

@Composable
fun getDrawableResourceId(picPath: String): Int {
    return when (picPath) {
        "storm" -> R.drawable.storm
        "cloudy" -> R.drawable.cloudy
        "sunny" -> R.drawable.few_sunny
        "rainy" -> R.drawable.rainy
        "windy" -> R.drawable.windy
        "cloudy_sunny" -> R.drawable.cloudy_sunny
        else -> R.drawable.few_sunny
    }
}