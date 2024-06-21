package com.example.weather.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.models.WeatherResponse
import com.example.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (private val weatherRepository: WeatherRepository) : ViewModel() {


    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: Flow<WeatherResponse?> = _weatherData

    private val _error = MutableStateFlow<String?>(null)
    val error: Flow<String?> = _error


    fun fetchData() {

        getCurrentLocation { latitude, longitude ->
            viewModelScope.launch {
                weatherRepository.getWeather(
                    latitude,
                    longitude,
                    object : Callback<WeatherResponse> {
                        override fun onResponse(
                            response: Response<WeatherResponse>?,
                            retrofit: Retrofit?
                        ) {
                            if (response?.isSuccess == true) {
                                _weatherData.value = response.body()

                                Log.i("Response Result", "$_weatherData")
                            } else {
                                _error.value = "Error ${response?.code()}: ${response?.message()}"
                            }
                        }

                        override fun onFailure(t: Throwable?) {
                            _error.value = "Network error: ${t?.message}"
                        }
                    })
            }

        }
    }

}
