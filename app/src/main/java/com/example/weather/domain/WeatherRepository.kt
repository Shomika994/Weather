package com.example.weather.domain

import com.example.weather.domain.util.Resource
import com.example.weather.domain.model.WeatherData

interface WeatherRepository {
    suspend fun fetchWeather(latitude: Double, longitude: Double): Resource<WeatherData>
}