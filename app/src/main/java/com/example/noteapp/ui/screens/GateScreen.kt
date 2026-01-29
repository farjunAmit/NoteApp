package com.example.noteapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.noteapp.ui.vm.AuthViewModel

@Composable
fun GateScreen(
    viewModel: AuthViewModel,
    onUserAlreadyLoggedIn: () -> Unit,
    onUserNotLoggedIn: () -> Unit
){
    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(uiState.value.isLoggedIn){
        if(uiState.value.isLoggedIn){
            onUserAlreadyLoggedIn()
        }else{
            onUserNotLoggedIn()
        }
    }
    Text("Checking user login status...")
}