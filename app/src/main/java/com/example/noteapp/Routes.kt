package com.example.noteapp

object Routes {
    const val GATE = "gate"
    const val NOTE = "note"
    const val LOGIN = "login"
    const val NOTE_DETAILS = "note_details/{noteId}"

    fun noteDetails(noteId: Int): String {
        return "note_details/$noteId"
    }
}