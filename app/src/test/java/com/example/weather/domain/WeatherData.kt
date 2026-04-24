package com.example.weather.domain

import com.example.weather.domain.model.WeatherData


object WeatherDataTest {
    fun create(
        icon: String? = "01d",
        main: String? = "Clear",
        description: String? = "Clear sky",
        temperature: Double = 20.0,
        humidity: Int = 50,
        tempMin: Double = 18.0,
        tempMax: Double = 22.0,
        windSpeed: Double = 0.0,
        name: String = "Moscow",
        country: String = "Russia",
        sunrise: Long = 1609459200L,
        sunset: Long = 1609545600L
    ) = WeatherData(
        icon = icon,
        main = main,
        description = description,
        temperature = temperature,
        humidity = humidity,
        tempMin = tempMin,
        tempMax = tempMax,
        windSpeed = windSpeed,
        name = name,
        country = country,
        sunrise = sunrise,
        sunset = sunset
    )
}
