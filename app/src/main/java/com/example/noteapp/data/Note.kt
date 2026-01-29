package com.example.noteapp.data
import java.time.LocalDate

class Note (
    val id : Int,
    var date : LocalDate = LocalDate.now(),
    var title : String,
    var body : String,
    var location : NoteLocation,
    val image : String? = null
)