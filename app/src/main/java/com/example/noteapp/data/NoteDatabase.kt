package com.example.noteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NoteTable::class], version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}