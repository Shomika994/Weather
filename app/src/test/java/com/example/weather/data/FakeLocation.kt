package com.example.weather.data

import android.location.Location
import com.example.weather.domain.location.LocationTracker

class FakeLocation(private val location: Location?): LocationTracker {
    override suspend fun getCurrentLocation(): Location? = location
}