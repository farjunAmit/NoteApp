package com.example.noteapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import com.example.noteapp.exceptions.NoteNotFoundException

class NoteDefaultRepository @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        val noteEntities = noteDao.getNotes()
        return noteEntities.map { entities ->
            entities.map { entity ->
                entity.toNote()
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        try {
            val noteEntity = noteDao.getNoteById(id)
            return noteEntity!!.toNote()
        } catch (e: Exception) {
            throw NoteNotFoundException()
        }
    }

    override suspend fun deleteNote(note: Note) {
        try {
            noteDao.deleteNote(note.toEntity())
        } catch (e: Exception) {
            throw NoteNotFoundException()
        }
    }

    override suspend fun insertNote(
        title: String,
        body: String,
        location: NoteLocation,
        date: LocalDate
    ) {
        val noteEntity = NoteTable(
            title = title,
            body = body,
            location = location,
            date = date
        )
        noteDao.upsertNote(noteEntity)
    }

    override suspend fun upsertNote(note: Note) {
        noteDao.upsertNote(note.toEntity())
    }


    override suspend fun createEmptyNote(location: NoteLocation): Note {
        val noteEntity = NoteTable(
            title = "",
            body = "",
            location = location
        )
        val newId = noteDao.insertNote(noteEntity).toInt()
        return noteEntity.copy(id = newId).toNote()
    }

}