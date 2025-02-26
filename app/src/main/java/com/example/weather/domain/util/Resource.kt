package com.example.weather.domain.util

import com.example.weather.domain.WeatherResponse
import retrofit2.Call

sealed class Resource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}