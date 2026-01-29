package com.example.noteapp.ui.ui_state

import com.example.noteapp.data.Note
import com.example.noteapp.exceptions.LocationError

data class NoteDetailUiState(
    val note: Note? = null,
    val locationError: LocationError? = null,
    val noteNotFoundError : Boolean = false,
    val isLoading : Boolean = true,
    val isNoteUpdated : Boolean = false
)
