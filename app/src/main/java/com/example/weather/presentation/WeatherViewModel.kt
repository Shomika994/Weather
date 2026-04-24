package com.example.weather.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.dataStore.DataStore
import com.example.weather.domain.location.LocationTracker
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val dataStore: DataStore
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set


    init {
        loadSavedResponse()
    }


    fun loadData() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true, error = null
            )
        try {
            locationTracker.getCurrentLocation()?.let { it ->
                when (val result = weatherRepository.fetchWeather(it.latitude, it.longitude)) {
                    is Resource.Success -> {
                        val weatherData = result.data
                            state = state.copy(
                                weatherData = weatherData, isLoading = false, error = null
                            )
                            weatherData.let {
                                dataStore.saveData(it)
                            }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            weatherData = null, isLoading = false, error = "Something went wrong." +
                            " Reason. " + result.message
                        )
                    }

                }
            } ?: run {
                state = state.copy(
                    weatherData = null,
                    isLoading = false,
                    error = "Couldn't retrieve location." + " Make sure to enable internet and grant permissions."
                )
            }
        } catch (e: Exception){
            state = state.copy(
                weatherData = null,
                isLoading = false,
                error = e.message
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




