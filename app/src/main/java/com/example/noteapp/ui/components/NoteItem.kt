package com.example.noteapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.noteapp.data.Note
@Composable
fun NoteItem(
    note: Note,
    onNoteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .border(2.dp, Color(0xFF1976D2), shape = RoundedCornerShape(8.dp)) // כחול חזק למסגרת
            .background(Color(0xFF2196F3), shape = RoundedCornerShape(8.dp))  // כחול קצת יותר בהיר בפנים
            .clickable(onClick = onNoteClick)
            .padding(12.dp)
    ) {
        Text(
            text = note.date.toString() + ": ",
            color = Color.White,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = note.title,
            color = Color.White
        )
    }
}
