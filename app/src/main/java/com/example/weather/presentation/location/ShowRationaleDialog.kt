package com.example.weather.presentation.location

import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ShowDialog(
    onGoToSettingsClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
        onDismissRequest = onDismissRequest,
        title = { Text("Permission Required") },
        text = {
            Text("It looks like you have turned off permissions required for this feature." +
                    "It can be enabled under Application Settings")
        },
        confirmButton = {
            TextButton(
                onClick = onGoToSettingsClick,
            ) {
                Text("GO TO SETTINGS")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClick
            ) {
                Text("CANCEL")
            }
        }
    )
}