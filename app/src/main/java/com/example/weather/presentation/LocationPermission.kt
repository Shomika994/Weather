package com.example.weather.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    LaunchedEffect(key1 = permissionState) {

        if (permissionState.shouldShowRationale) {
            onPermissionDenied()
        } else {
            val permissionsToRequest = permissionState.permissions.filter {
                !it.status.isGranted
            }

            if (permissionsToRequest.isNotEmpty()) {
                permissionState.launchMultiplePermissionRequest()
            }

            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
            }
        }
    }
}


fun isLocationEnabled(activity: Context): Boolean {
    val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun getCurrentLocation(callback: (Double, Double) -> Unit) {

    val accuracy = Priority.PRIORITY_HIGH_ACCURACY

    try {
        fusedLocationProviderClient.getCurrentLocation(
            accuracy, CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            location?.let {
                val latitude = location.latitude
                Log.i("Latitude", "${location.latitude}")
                val longitude = location.longitude
                Log.i("Longitude", "${location.longitude}")
                callback(latitude, longitude)
            }
        }
    } catch (e: SecurityException) {
        print("Location permission denied")
    }
}


fun showRationaleDialog(context: Context) {
    AlertDialog.Builder(context)
        .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
        .setPositiveButton(
            "GO TO SETTINGS"
        ) { _, _ ->
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }.show()
}

