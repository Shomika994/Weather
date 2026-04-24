package com.example.weather.data.location

import android.content.Context
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
fun isLocationAvail(context: Context): Boolean{
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isLocationEnabled
}