package com.example.weather.domain

import android.location.Location


interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}