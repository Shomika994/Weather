package com.example.weather.presentation

import com.example.weather.domain.model.WeatherData


data class WeatherState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)