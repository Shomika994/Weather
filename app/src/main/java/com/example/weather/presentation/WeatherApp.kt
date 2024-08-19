package com.example.weather.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.R
import com.example.weather.models.WeatherResponse
import com.example.weather.network.Network



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp (context: Context, weatherResponse: WeatherResponse?){

    val primaryColor = colorResource(id = R.color.colorPrimary)

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "WeatherApp",
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Cyan
            ))
    }, content = { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
           if (Network.isOnline(context)){
               RequestLocationPermission(onPermissionGranted = { WeatherScreen(weatherResponse) },
                   onPermissionDenied = { showRationaleDialog(context) })
           }else{
               Toast.makeText(context, "No network available.", Toast.LENGTH_SHORT).show()
           }
        }
    })
}

