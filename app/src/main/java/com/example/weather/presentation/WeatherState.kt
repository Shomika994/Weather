package com.example.weather.presentation

import com.example.weather.domain.WeatherResponse

data class WeatherState(
    val weatherResponse: WeatherResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)