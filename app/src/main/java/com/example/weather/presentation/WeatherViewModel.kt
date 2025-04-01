package com.example.weather.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.DataStore
import com.example.weather.domain.LocationTracker
import com.example.weather.domain.WeatherRepository
import com.example.weather.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val dataStore: DataStore
) : ViewModel() {

    var state by mutableStateOf(WeatherState())

    init {
        loadSavedResponse()
    }

    fun loadData() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true, error = null
            )

            locationTracker.getCurrentLocation()?.let { it ->
                when (val result = weatherRepository.fetchWeather(it.latitude, it.longitude)) {
                    
                    is Resource.Success -> {
                        val weatherData = result.data
                        state = state.copy(
                            weatherData = weatherData,
                            isLoading = false,
                            error = null
                        )
                        weatherData?.let {
                            dataStore.saveData(it)
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            weatherData = null,
                            isLoading = false,
                            error = result.message
                        )
                    }

                }
            } ?: run {
                state = state.copy(
                    weatherData = null,
                    isLoading = false,
                    error = "Couldn't retrieve location." + " Make sure to enable GPS and grant permissions."
                )
            }
        }
    }

    private fun loadSavedResponse() {
        viewModelScope.launch {
            dataStore.weatherDataFlow.collect { savedWeather ->
                savedWeather.let {
                    state = state.copy(weatherData = it)
                }
            }
        }
    }

}


