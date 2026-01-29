package com.example.noteapp.ui.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteLocation
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.UserLocationRepository
import com.example.noteapp.exceptions.LocationError
import com.example.noteapp.exceptions.NoLocationPermissionException
import com.example.noteapp.exceptions.LocationIsDisabledException
import com.example.noteapp.exceptions.UnknownLocationErrorException
import com.example.noteapp.ui.ui_state.NoteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val userLocationRepository: UserLocationRepository
) : ViewModel() {
    private val _noteUiState = MutableStateFlow(NoteUiState())
    val noteUiState: StateFlow<NoteUiState> = _noteUiState
    val notes: StateFlow<List<Note>> =
        noteRepository.getNotes()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun loadUserCurrentLocation(context: Context) {
        viewModelScope.launch {
            try {
                val location = userLocationRepository.getUserLocation(context)
                _noteUiState.update { it.copy(userCurrentLocation = location) }
            } catch (e: NoLocationPermissionException) {
                _noteUiState.update { it.copy(locationError = LocationError.NO_LOCATION_PERMISSION) }
            } catch (e: LocationIsDisabledException) {
                _noteUiState.update { it.copy(locationError = LocationError.LOCATION_DISABLED) }
            } catch (e: UnknownLocationErrorException) {
                _noteUiState.update { it.copy(locationError = LocationError.UNKNOWN_ERROR) }
            }
        }
    }

    fun createEmptyNote(context: Context) {
        viewModelScope.launch {
            try {
                val date = LocalDate.now()
                val location = userLocationRepository.getUserLocation(context)
                val note = noteRepository.createEmptyNote(location)
                _noteUiState.update { it.copy(newNote = note) }
            } catch (e: NoLocationPermissionException) {
                _noteUiState.update { it.copy(locationError = LocationError.NO_LOCATION_PERMISSION) }
            } catch (e: LocationIsDisabledException) {
                _noteUiState.update { it.copy(locationError = LocationError.LOCATION_DISABLED) }
            } catch (e: UnknownLocationErrorException) {
                _noteUiState.update { it.copy(locationError = LocationError.UNKNOWN_ERROR) }
            }
        }
    }

    fun clearLocationError() {
        _noteUiState.update { it.copy(locationError = null) }
    }

    fun consumeNewNote() {
        _noteUiState.update { it.copy(newNote = null) }
    }
}