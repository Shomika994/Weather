package com.example.weather.domain.util

import java.util.Locale

fun getUnit(): String {
    val country = Locale.getDefault().country
    return when (country) {
        "US", "LR", "MM" -> "°F"
        else -> "°C"
    }
}
