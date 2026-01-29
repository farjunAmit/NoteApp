package com.example.noteapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteLocation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Composable
fun NoteMap(
    notes: List<Note>,
    currentLocation: NoteLocation? = null,
    onNoteClick: (Int) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState()
    LaunchedEffect(currentLocation) {
        if (currentLocation != null) {
            val location = LatLng(currentLocation.latitude, currentLocation.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(location, 15f)
        }
    }
    if (currentLocation != null) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            notes.forEach { note ->
                key(note.id) {
                    val location = LatLng(note.location.latitude, note.location.longitude)
                    MarkerComposable(
                        state = rememberMarkerState(position = location),
                        onClick = {
                            onNoteClick(note.id)
                            true
                        }
                    ) {
                        NoteMarker(note)
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Loading Map...")
        }
    }
}