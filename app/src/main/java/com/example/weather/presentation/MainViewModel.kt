package com.example.weather.presentation

import android.app.Dialog
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.R
import com.example.weather.models.WeatherResponse
import com.example.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadData()
    }

    private fun loadData() {

        getCurrentLocation { latitude, longitude ->
            CoroutineScope(Dispatchers.IO).launch {
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}
