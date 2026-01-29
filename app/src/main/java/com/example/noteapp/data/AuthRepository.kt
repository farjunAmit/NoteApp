package com.example.noteapp.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult
    fun signOut()
    fun getCurrentUser(): FirebaseUser?
}