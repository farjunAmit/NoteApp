package com.example.noteapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyNoteState(
    onClick: () -> Unit
){
    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("No notes yet")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Start by adding a note")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onClick() }
        ) {
            Text("Add Note")
        }
    }
}