package com.example.noteapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.noteapp.data.Note

@Composable
fun NoteMarker(note: Note) {
    Column(
        modifier = Modifier
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color.Red, CircleShape)
        )
        Text(
            text = note.title,
            color = Color.Black,
        )
    }
}
