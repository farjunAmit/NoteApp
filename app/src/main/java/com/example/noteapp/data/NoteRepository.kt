package com.example.noteapp.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun deleteNote(note: Note)
    suspend fun insertNote(title: String, body: String, location: NoteLocation, date: LocalDate)
    suspend fun upsertNote(note: Note)

    suspend fun createEmptyNote(location: NoteLocation) : Note

}