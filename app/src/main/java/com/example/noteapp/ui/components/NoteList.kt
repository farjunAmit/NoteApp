package com.example.noteapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteapp.data.Note

@Composable
fun NoteList(
    notes: List<Note>,
    onNoteClick: (Int) -> Unit,
){
    LazyColumn(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
        items(notes, key = {note -> note.id}){note ->
            NoteItem(note = note, onNoteClick = { onNoteClick(note.id) })
        }
    }
}