package com.adrianhelo.weatherapp.presentation.util.WeatherIcons

import com.adrianhelo.weather.R

fun getWeatherIcon(iconId: String?): Int{
    return when (iconId) {
        "01d" -> R.drawable.few_sunny
        "01n" -> R.drawable.few_sunny
        "02d" -> R.drawable.few_sunny
        "02n" -> R.drawable.few_sunny
        "03d" -> R.drawable.cloudy_sunny
        "03n" -> R.drawable.cloudy_sunny
        "04d" -> R.drawable.cloudy
        "04n" -> R.drawable.cloudy
        "05d" -> R.drawable.cloudy_sunny
        "05n" -> R.drawable.cloudy_sunny
        "09d" -> R.drawable.rainy
        "09n" -> R.drawable.rainy
        "10d" -> R.drawable.rainy
        "10n" -> R.drawable.rainy
        "11d" -> R.drawable.storm
        "11n" -> R.drawable.storm
        "13d" -> R.drawable.snowy
        "13n" -> R.drawable.snowy
        "50d" -> R.drawable.storm
        "50n" -> R.drawable.storm
        else -> R.drawable.few_sunny
    }
}