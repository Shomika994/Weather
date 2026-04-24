package com.example.weather.data

import com.example.weather.domain.WeatherDataTest
import com.example.weather.domain.model.WeatherData
import com.example.weather.domain.repository.WeatherRepository
import com.example.weather.domain.util.Resource

class FakeRepository(private val shouldReturnError: Boolean): WeatherRepository {
    override suspend fun fetchWeather(latitude: Double, longitude: Double,
    ): Resource<WeatherData> {
        return if(shouldReturnError){
            Resource.Error("Error", null)
        } else {
            Resource.Success(
                data = WeatherDataTest.create()
            )
        }
    }

}