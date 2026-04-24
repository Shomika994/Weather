package com.example.weather.domain.repository

import com.example.weather.domain.model.WeatherData
import com.example.weather.domain.util.Resource

interface WeatherRepository {
    suspend fun fetchWeather(latitude: Double, longitude: Double): Resource<WeatherData>
}