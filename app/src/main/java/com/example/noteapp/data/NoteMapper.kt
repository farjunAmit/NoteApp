package com.example.noteapp.data

import java.time.LocalDate

fun Note.toEntity(): NoteTable {
    return NoteTable(
        id = id,
        date = date,
        title = title,
        body = body,
        location = location,
        image = image
    )
}

fun NoteTable.toNote(): Note{
    return Note(
        id = id,
        date = date,
        title = title,
        body = body,
        location = location,
        image = image
    )
}
