package com.example.noteapp.ui.screens

import android.R.attr.navigationIcon
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.noteapp.ui.components.NoteList
import com.example.noteapp.ui.components.NoteMap
import com.example.noteapp.ui.vm.NoteScreenViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteLocation
import com.example.noteapp.ui.components.EmptyNoteState
import com.example.noteapp.ui.components.PopUpError
import com.example.noteapp.ui.vm.AuthViewModel

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    noteScreenViewModel: NoteScreenViewModel,
    onNoteClick: (Int) -> Unit,
    onLogout: () -> Unit
) {
    val uiState = noteScreenViewModel.noteUiState.collectAsState().value
    val notes by noteScreenViewModel.notes.collectAsState()
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            noteScreenViewModel.clearLocationError()
            noteScreenViewModel.loadUserCurrentLocation(context)
        }
    }
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val currentLocation = uiState.userCurrentLocation
    val locationError = uiState.locationError
    LaunchedEffect(selectedItemIndex) {
        if (selectedItemIndex == 1) {
            noteScreenViewModel.loadUserCurrentLocation(context)
        }
    }
    LaunchedEffect(uiState.newNote) {
        if (uiState.newNote != null) {
            noteScreenViewModel.consumeNewNote()   // consume the new note to avoid showing it again and again
            onNoteClick(uiState.newNote.id)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes") },
                navigationIcon = {
                    IconButton(onClick = {
                        noteScreenViewModel.clearLocationError()
                        onLogout()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        },
        bottomBar = {
            val bottomNavItems = listOf(
                BottomNavItem(
                    title = "Note List",
                    selectedIcon = Icons.AutoMirrored.Filled.List,
                    unselectedIcon = Icons.AutoMirrored.Outlined.List
                ),
                BottomNavItem(
                    title = "Note Map",
                    selectedIcon = Icons.Filled.Place,
                    unselectedIcon = Icons.Outlined.Place
                )
            )
            NavigationBar() {
                bottomNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(text = item.title) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val note = noteScreenViewModel.createEmptyNote(context)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { innerPadding ->
        if (locationError != null) {
            PopUpError(
                locationError = locationError,
                permissionLauncher = permissionLauncher,
                context = context,
                onDismissOrConfirm = { noteScreenViewModel.clearLocationError() }
            )
        }
        if (notes.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                EmptyNoteState(onClick = {
                    noteScreenViewModel.createEmptyNote(context)
                })
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                ScreenContent(
                    index = selectedItemIndex,
                    notes = notes,
                    currentLocation = currentLocation,
                    onNoteClick = onNoteClick
                )
            }
        }
    }
}

@Composable
fun ScreenContent(
    index: Int,
    notes: List<Note>,
    currentLocation: NoteLocation? = null,
    onNoteClick: (Int) -> Unit
) {
    val listIndex = 0
    val mapIndex = 1
    when (index) {
        listIndex -> NoteList(notes = notes, onNoteClick = onNoteClick)
        mapIndex -> NoteMap(
            notes = notes,
            currentLocation = currentLocation,
            onNoteClick = onNoteClick
        )
    }
}