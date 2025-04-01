package com.example.weather.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.data.NetworkAccess
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun WeatherApp(
    viewModel: WeatherViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {

    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val hasLocationPermission = permissions.allPermissionsGranted
    val coroutineScope = rememberCoroutineScope()
    val isOnline = remember { mutableStateOf(NetworkAccess.isOnline(context)) }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            viewModel.loadData()
        } else if (permissions.permissions.any { !it.status.isGranted && it.status.shouldShowRationale }) {
            coroutineScope.launch {
                Toast.makeText(context, "Show rationale", Toast.LENGTH_SHORT).show()
            }
        } else {
            permissions.launchMultiplePermissionRequest()
        }
    }

    if (!isOnline.value) {
        coroutineScope.launch {
            Toast.makeText(
                context,
                "Network not available. Please check your internet settings.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "WeatherApp", fontWeight = FontWeight.Bold, color = Color.White)
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.DarkGray
            )
        )
    }, content = { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isOnline.value) {
                WeatherScreen(viewModel.state)
            }
        }
    })
}


