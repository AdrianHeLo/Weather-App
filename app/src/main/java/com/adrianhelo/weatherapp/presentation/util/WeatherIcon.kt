package com.adrianhelo.weatherapp.presentation.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.adrianhelo.weather.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.errorprone.annotations.Modifier

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherIcon(iconCode: String?) {
    // Construimos la URL del icono (ejemplo: "04d" -> url de OpenWeather)
    val iconUrl = "https://openweathermap.org/img/wn/$iconCode@4x.png"

    GlideImage(
        model = iconUrl,
        contentDescription = null,
        modifier = androidx.compose.ui.Modifier.size(150.dp)
    )
}