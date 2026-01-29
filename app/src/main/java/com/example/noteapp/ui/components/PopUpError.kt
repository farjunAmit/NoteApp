package com.example.noteapp.ui.components

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.noteapp.exceptions.LocationError

@Composable
fun PopUpError(
    locationError: LocationError,
    permissionLauncher: ActivityResultLauncher<String>,
    context: Context,
    onDismissOrConfirm: () -> Unit, //Dismiss and Confirm do the same in this case
){
    when (locationError) {
        LocationError.NO_LOCATION_PERMISSION -> AlertDialog(
            onDismissRequest = { onDismissOrConfirm },
            title = { Text("Permission is required") },
            text = { Text("Please grant location permission to use this feature, and try again") },
            confirmButton = {
                TextButton(onClick = {
                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }) { Text("give permission") }
            },
            dismissButton = {
                TextButton(onClick = { onDismissOrConfirm }) { Text("cancel") }
            }
        )

        LocationError.LOCATION_DISABLED -> AlertDialog(
            onDismissRequest = { onDismissOrConfirm },
            title = { Text("Location is disabled") },
            text = { Text("Please enable location to use this feature, and try again") },
            confirmButton = {
                TextButton(onClick = {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }) { Text("go to settings") }
            },
            dismissButton = {
                TextButton(onClick = { onDismissOrConfirm }) { Text("cancel") }
            }
        )
        LocationError.UNKNOWN_ERROR -> AlertDialog(
            onDismissRequest = { onDismissOrConfirm },
            title = { Text("Locating Error") },
            text = { Text("Unknown error occurred while locating your location.") },
            confirmButton = {
                TextButton(onClick = { onDismissOrConfirm }) { Text("ok") }
            }
        )
        else -> Unit
    }
}