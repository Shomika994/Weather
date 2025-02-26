package com.example.weather.data

import com.example.weather.di.AppModule
import com.example.weather.domain.util.Resource
import com.example.weather.domain.WeatherRepository
import com.example.weather.domain.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {

    override suspend fun fetchWeather(
        latitude: Double,
        longitude: Double,
    ): Resource<WeatherResponse> {
        return try {
            Resource.Success(
                data = weatherService.getWeather(
                    latitude,
                    longitude,
                    AppModule.METRIC_UNIT,
                    AppModule.API_ID
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error( e.message ?: "An unknown error occurred")
        }
    }
}