package com.example.weather.domain.util

import com.example.weather.R

fun getWeatherIcon(icon: String): Int {
    return when (icon) {
        "01d" -> R.drawable.sunny
        "02d" -> R.drawable.cloud
        "03d" -> R.drawable.cloud
        "04d", "04n" -> R.drawable.cloud
        "10d" -> R.drawable.rain
        "11d", "11n" -> R.drawable.storm
        "13d", "13n" -> R.drawable.snowflakebk
        "01n", "02n", "03n", "10n" -> R.drawable.cloud
        else -> R.drawable.cloud
    }
}