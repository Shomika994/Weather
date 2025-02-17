package com.example.weather.repository

import android.app.Application
import com.example.weather.R
import com.example.weather.models.WeatherResponse
import com.example.weather.network.ApiClient
import com.example.weather.network.WeatherService
import retrofit.Call
import retrofit.Callback
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {

    init {
        println("Hello from $weatherService")
    }

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        callback: Callback<WeatherResponse>
    ) {

        val call: Call<WeatherResponse> =
            weatherService.getWeather(latitude, longitude, ApiClient.API_ID, ApiClient.METRIC_UNIT)
        call.enqueue(callback)
    }
}