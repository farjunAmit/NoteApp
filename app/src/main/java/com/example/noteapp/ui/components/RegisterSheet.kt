package com.example.noteapp.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterSheet(
    onDismiss: () -> Unit,
    onRegister: (email: String, password: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") })
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        if (password != confirmPassword) {
            Text("Passwords do not match")
        }
        Button(
            onClick = {
                onRegister(email, password)
                email = ""
                password = ""
                confirmPassword = ""
            },
            enabled = password == confirmPassword && password.isNotBlank() && email.isNotBlank()
        ) {
            Text("Register")
        }
    }
}