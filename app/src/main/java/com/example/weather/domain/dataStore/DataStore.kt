package com.example.weather.domain.dataStore

import com.example.weather.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface DataStore {
    suspend fun saveData(weatherData: WeatherData?)
    val weatherDataFlow: Flow<WeatherData?>
}