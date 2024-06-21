package com.example.weather.repository

import com.example.weather.models.WeatherResponse
import retrofit.Callback

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double, callback: Callback<WeatherResponse>)
}