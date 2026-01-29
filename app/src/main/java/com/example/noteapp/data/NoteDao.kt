package com.example.noteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM NoteTable ORDER BY date DESC")
    fun getNotes(): Flow<List<NoteTable>>
    @Query("SELECT * FROM NoteTable WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteTable?
    @Delete
    suspend fun deleteNote(note: NoteTable)
    @Upsert
    suspend fun upsertNote(note: NoteTable)
    @Insert
    suspend fun insertNote(note: NoteTable): Long

}