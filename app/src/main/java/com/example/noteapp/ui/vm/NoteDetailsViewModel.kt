package com.example.noteapp.ui.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.UserLocationRepository
import com.example.noteapp.exceptions.LocationError
import com.example.noteapp.exceptions.LocationIsDisabledException
import com.example.noteapp.exceptions.NoLocationPermissionException
import com.example.noteapp.exceptions.NoteNotFoundException
import com.example.noteapp.exceptions.UnknownLocationErrorException
import com.example.noteapp.ui.ui_state.NoteDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val userLocationRepository: UserLocationRepository
) : ViewModel() {
    private val _noteDetailUiState = MutableStateFlow(NoteDetailUiState())
    val noteDetailUiState: StateFlow<NoteDetailUiState> = _noteDetailUiState

    fun loadNote(noteId: Int) {
        _noteDetailUiState.update {
            it.copy(
                isLoading = true,
                noteNotFoundError = false,
                locationError = null,
                isNoteUpdated = false,
                note = null
            )
        }
        viewModelScope.launch {
            try {
                val note = noteRepository.getNoteById(noteId)
                _noteDetailUiState.value = _noteDetailUiState.value.copy(note = note)
            } catch (e: NoteNotFoundException) {
                _noteDetailUiState.value = _noteDetailUiState.value.copy(noteNotFoundError = true)
            }
            finally {
                _noteDetailUiState.value = _noteDetailUiState.value.copy(isLoading = false)
            }
        }
    }

    fun updateNote(title: String, body: String, date: LocalDate, context: Context) {
        viewModelScope.launch {
            try {
                val current = _noteDetailUiState.value.note ?: return@launch
                val updated = current.copy(
                    title = title,
                    body = body,
                    date = date,
                    location = userLocationRepository.getUserLocation(context)
                )
                noteRepository.upsertNote(updated)
                _noteDetailUiState.update {
                    it.copy(note = updated, isNoteUpdated = true)
                }
            } catch (e: NoLocationPermissionException) {
                _noteDetailUiState.update { it.copy(locationError = LocationError.NO_LOCATION_PERMISSION) }
            } catch (e: LocationIsDisabledException) {
                _noteDetailUiState.update { it.copy(locationError = LocationError.LOCATION_DISABLED) }
            } catch (e: UnknownLocationErrorException) {
                _noteDetailUiState.update { it.copy(locationError = LocationError.UNKNOWN_ERROR) }
            }
        }
    }

    fun clearLocationError() {
        _noteDetailUiState.update { it.copy(locationError = null) }
    }

    fun deleteNote() {
        viewModelScope.launch {
            try {
                val note = _noteDetailUiState.value.note
                if (note != null) {
                    noteRepository.deleteNote(note)
                    _noteDetailUiState.update { it.copy(isNoteUpdated = true) }
                }
            } catch (e: NoteNotFoundException) {
                _noteDetailUiState.update { it.copy(noteNotFoundError = true) }
            }
        }
    }

    fun consumeNoteUpdated() {
        _noteDetailUiState.update { it.copy(isNoteUpdated = false) }
    }
}
