package com.example.noteapp.data

import android.content.Context

interface UserLocationRepository {
    suspend fun getUserLocation(context: Context): NoteLocation
}