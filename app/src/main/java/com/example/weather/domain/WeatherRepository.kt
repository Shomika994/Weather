package com.example.weather.domain

import com.example.weather.domain.util.Resource

interface WeatherRepository {
    suspend fun fetchWeather(latitude: Double, longitude: Double): Resource<WeatherResponse>
}