package com.example.weather.data.location

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.weather.data.network.NetworkAccess
import com.example.weather.data.location.isLocationAvail
import com.example.weather.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DefaultLocationTracker @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {


    @RequiresApi(Build.VERSION_CODES.P)
    override suspend fun getCurrentLocation(): Location? {
        val hasFinePermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarsePermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFinePermission || !hasCoarsePermission || !NetworkAccess.isOnline(application)) {
            return null
        }


        return suspendCancellableCoroutine { cont ->

            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 1000
            ).build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    if (cont.isActive) {
                        locationResult.lastLocation?.let { location ->
                            cont.resume(location)
                        } ?: cont.resume(null)
                    }
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                    if (!locationAvailability.isLocationAvailable) {
                        Log.e("Location", "Location is not available")
                    }
                }
            }
            val locationAvailable = isLocationAvail(application)
            if (locationAvailable) {
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper()
                )
                cont.invokeOnCancellation {
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                }
            } else {
                cont.resumeWithException(
                    SecurityException(
                        "Location not available."
                                + " Enable location"
                    )
                )
            }
        }

        }
}