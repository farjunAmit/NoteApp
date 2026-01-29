package com.example.noteapp.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDefaultRepository @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser

    override fun signOut() = auth.signOut()

    override suspend fun login(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun register(email: String, password: String): AuthResult {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

}