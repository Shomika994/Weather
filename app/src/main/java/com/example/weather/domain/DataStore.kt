package com.example.weather.domain

import kotlinx.coroutines.flow.Flow

interface DataStore {
    suspend fun saveData(weatherResponse: WeatherResponse)
    val weatherResponseFlow: Flow<WeatherResponse?>
}