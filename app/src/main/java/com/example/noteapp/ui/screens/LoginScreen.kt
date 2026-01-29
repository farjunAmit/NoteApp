package com.example.noteapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.noteapp.ui.components.RegisterSheet
import com.example.noteapp.ui.vm.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
) {
    val uiState by authViewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showRegisterSheet by remember { mutableStateOf(false) }
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            onLoginSuccess()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Login") })
        }
    ) { innerPadding ->
        if (showRegisterSheet) {
            RegisterSheet(
                onDismiss = { showRegisterSheet = false },
                onRegister = { email, password ->
                    authViewModel.register(email, password)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter your email") },
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter your password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (uiState.isLoading) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "Loading...")
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { authViewModel.login(email, password) },
                    ) {
                        Text(text = "Login")
                    }
                    Button(
                        onClick = { showRegisterSheet = true },
                    )
                    {
                        Text(text = "Register")
                    }
                }
            }
            uiState.error?.let { msg ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = msg)
            }
        }
    }
}