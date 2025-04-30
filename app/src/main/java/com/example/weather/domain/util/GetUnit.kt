package com.example.weather.domain.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getUnit(): String {
    val currentLocale = LocalContext.current.resources.configuration.locales.get(0)

    return when (currentLocale.language) {
        "US", "LR", "MM", "BS", "BZ", "PW", "KY", "FM", "MH" -> "°F"
        else -> "°C"
    }
}
