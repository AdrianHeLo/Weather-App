package com.adrianhelo.weatherapp.data

import com.adrianhelo.weatherapp.domain.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceImp {
    @GET("weather")
    suspend fun getFetchWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("lang") lang: String, @Query("apiKey") apiKey: String): Response<WeatherResponse>
}
