package com.example.noteapp.ui.ui_state

import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteLocation
import com.example.noteapp.exceptions.LocationError

data class NoteUiState(
    val notes : List<Note> = emptyList(),
    val userCurrentLocation : NoteLocation? = null,
    val locationError : LocationError? = null,
    val newNote : Note? = null
)
