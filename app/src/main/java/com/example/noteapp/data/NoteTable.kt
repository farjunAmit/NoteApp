package com.example.noteapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class NoteTable(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val date : LocalDate = LocalDate.now(),
    val title : String,
    val body : String,
    @Embedded
    val location : NoteLocation,
    val image : String? = null
)