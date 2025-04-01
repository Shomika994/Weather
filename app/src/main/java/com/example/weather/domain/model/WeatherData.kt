package com.example.weather.domain.model

data class WeatherData(
    val icon: String?,
    val main: String?,
    val description: String?,
    val temperature: Double,
    val humidity: Int,
    val tempMin: Double,
    val tempMax: Double,
    val windSpeed: Double,
    val name: String,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)