package com.example.weather.data

import com.example.weather.data.mappers.toDomainWeather
import com.example.weather.di.AppModule
import com.example.weather.domain.WeatherRepository
import com.example.weather.domain.util.Resource
import com.example.weather.domain.model.WeatherData
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {

    override suspend fun fetchWeather(
        latitude: Double,
        longitude: Double,
    ): Resource<WeatherData> {
        return runCatching {
            Resource.Success(
                data = weatherService.getWeather(
                    latitude,
                    longitude,
                    AppModule.METRIC_UNIT,
                    AppModule.API_ID
                ).toDomainWeather()
            )
        }.getOrElse{ e ->
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}