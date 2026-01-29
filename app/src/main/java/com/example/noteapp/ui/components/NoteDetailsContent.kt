package com.example.noteapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.data.Note
import java.time.LocalDate

@Composable
fun NoteDetailsContent(
    note: Note,
    onSaveClick: (String, String, LocalDate) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .border(2.dp, Color(0xFF1976D2))
    ) {

        var title by remember { mutableStateOf(note.title) }
        var isTitleEdited by remember { mutableStateOf(false) }
        var body by remember { mutableStateOf(note.body) }
        var isBodyEdited by remember { mutableStateOf(false) }
        var showDatePicker by remember { mutableStateOf(false) }
        var date by remember { mutableStateOf(note.date) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2196F3))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showDatePicker) {
                    NoteDatePicker(
                        date = date,
                        onDateSelected = { newDate ->
                            date = newDate
                        },
                        onDismiss = { showDatePicker = false }
                    )
                } else {
                    Text(
                        text = date.toString(),
                        modifier = Modifier.weight(1f),
                        color = Color.White
                    )
                    IconButton(
                        onClick = { showDatePicker = !showDatePicker },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Title"
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isTitleEdited) {
                    TextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { isTitleEdited = !isTitleEdited },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Title"
                        )
                    }
                } else {
                    Text(
                        text = title.ifEmpty { "Empty Title" },
                        color = if (title.isEmpty()) Color(0xCCFFFFFF) else Color.White,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(
                        onClick = { isTitleEdited = !isTitleEdited },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Title"
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isBodyEdited) {
                    TextField(
                        value = body,
                        onValueChange = {
                            body = it
                        },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { isBodyEdited = !isBodyEdited },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Body"
                        )
                    }
                } else {
                    Text(
                        text = body.ifEmpty { "Empty Body" },
                        color = if (body.isEmpty()) Color(0xCCFFFFFF) else Color.White,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { isBodyEdited = !isBodyEdited },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Body"
                        )
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onDeleteClick
                ) {
                    Text(text = "Delete")
                }

                Button(
                    onClick = { onSaveClick(title, body, date) }
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}