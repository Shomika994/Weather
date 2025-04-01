package com.example.weather.data.mappers

import com.example.weather.data.WeatherResponse
import com.example.weather.domain.model.WeatherData

fun WeatherResponse.toDomainWeather(): WeatherData {
    return WeatherData(
        icon = weather.firstOrNull()?.icon ?: "",
        main = weather.firstOrNull()?.main ?: "",
        description = weather.firstOrNull()?.description ?: "",
        temperature = main.temp,
        humidity = main.humidity,
        tempMin = main.tempMin,
        tempMax = main.tempMax,
        windSpeed = wind.speed,
        name = name,
        country = sys.country,
        sunrise = sys.sunrise,
        sunset = sys.sunset
    )
}