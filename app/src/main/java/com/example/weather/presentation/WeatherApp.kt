package com.example.weather.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.weather.data.NetworkAccess


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(state: WeatherState) {

    val context = LocalContext.current
    val isOnline = remember { mutableStateOf(NetworkAccess.isOnline(context)) }

    LaunchedEffect(isOnline.value) {
        if (!isOnline.value) {
            Toast.makeText(
                context,
                "Network not available." + " Please check your internet settings.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "WeatherApp", fontWeight = FontWeight.Bold, color = Color.White)
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Magenta
            )
        )
    }, content = { innerPadding ->
        Surface (
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            if (isOnline.value) {
                WeatherScreen(state)
            }
        }
    })
}


