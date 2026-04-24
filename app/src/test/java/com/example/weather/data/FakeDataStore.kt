package com.example.weather.data

import com.example.weather.domain.dataStore.DataStore
import com.example.weather.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class FakeDataStore : DataStore {

    private val _weatherDataFlow = MutableStateFlow<WeatherData?>(null)
    override suspend fun saveData(weatherData: WeatherData?) {
        _weatherDataFlow.value = weatherData
    }
    override val weatherDataFlow: Flow<WeatherData?> = _weatherDataFlow.asStateFlow()
    }


