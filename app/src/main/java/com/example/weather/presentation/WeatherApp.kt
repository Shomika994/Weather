package com.example.weather.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.data.NetworkAccess
import com.example.weather.presentation.location.ShowDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun WeatherApp(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    var showRationaleDialog by remember { mutableStateOf(false) }
    val isOnline = remember { mutableStateOf(NetworkAccess.isOnline(context)) }
    val coroutine = rememberCoroutineScope()
    val state = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutine.launch {
            viewModel.loadData()
            isRefreshing = false
        }
    }

    LaunchedEffect(isOnline.value) {
        if (!isOnline.value) {
            Toast.makeText(
                context,
                "Network not available. Please check your internet settings.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(permissions.allPermissionsGranted) {

        when {

            permissions.allPermissionsGranted -> {
                viewModel.loadData()
            }

            permissions.shouldShowRationale -> {
                showRationaleDialog = true
            }

            else -> {
                permissions.launchMultiplePermissionRequest()
            }
        }

    }

    if (showRationaleDialog){
        ShowDialog(
            onGoToSettingsClick = {
                runCatching {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                    showRationaleDialog = false
                }.getOrElse { e ->
                    e.printStackTrace()
                    Log.e("Error occurred", "${e.message}")
                }
            },
            onCancelClick = {showRationaleDialog = false},
            onDismissRequest = {showRationaleDialog = false}
        )
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "WeatherApp",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }, content = {

        Box(
            modifier
                .fillMaxSize()
                .padding(it)
                .pullToRefresh(
                    state = state, isRefreshing = isRefreshing, onRefresh = onRefresh
                )
        ) {
            Column(
                modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {

                if (isRefreshing) {
                    LinearProgressIndicator(
                        modifier.fillMaxWidth()
                    )
                } else {
                    LinearProgressIndicator(
                        modifier = modifier.fillMaxWidth(),
                        progress = { state.distanceFraction },
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (isOnline.value && !viewModel.state.isLoading) {
                    WeatherScreen(viewModel.state)
                }

                Spacer(modifier.height(300.dp))
            }

            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier.align(Alignment.Center)
                )
            }

            viewModel.state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = modifier.align(Alignment.Center)
                )
            }

        }
    })
}


