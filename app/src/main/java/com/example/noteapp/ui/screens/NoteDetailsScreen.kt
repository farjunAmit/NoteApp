package com.example.noteapp.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.noteapp.ui.vm.NoteDetailsViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.noteapp.ui.components.NoteDetailsContent
import com.example.noteapp.ui.components.PopUpError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    noteId: Int,
    noteDetailsViewModel: NoteDetailsViewModel,
    onSaveClick: () -> Unit,
    onBack: () -> Unit
) {
    val uiState = noteDetailsViewModel.noteDetailUiState.collectAsState().value
    val note = uiState.note
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            noteDetailsViewModel.clearLocationError()
        }
    }
    LaunchedEffect(key1 = noteId) {
        noteDetailsViewModel.loadNote(noteId)
    }

    LaunchedEffect(uiState.isNoteUpdated) {
        if (uiState.isNoteUpdated) {
            noteDetailsViewModel.consumeNoteUpdated()
            onSaveClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Details") },
                navigationIcon = {
                    IconButton(onClick = {
                        noteDetailsViewModel.clearLocationError()
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    )
    { innerPadding ->
        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Loading...")
                }
            }

            uiState.noteNotFoundError -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Note not found")
                }
            }

            uiState.locationError != null -> {
                PopUpError(
                    locationError = uiState.locationError,
                    permissionLauncher = permissionLauncher,
                    context = context,
                    onDismissOrConfirm = { noteDetailsViewModel.clearLocationError() }
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    NoteDetailsContent(
                        modifier = Modifier.fillMaxWidth(),
                        note = note!!,
                        onSaveClick = { title, body, date ->
                            noteDetailsViewModel.updateNote(title, body, date, context)
                        },
                        onDeleteClick = {
                            noteDetailsViewModel.deleteNote()
                        }
                    )
                }
            }
        }
    }
}