package com.example.noteapp.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDatePicker(
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val initialDateMillis = date
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis
                val selectedLocalDate = if (datePickerState.selectedDateMillis != null) {
                    Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                } else {
                    date
                }
                onDateSelected(selectedLocalDate)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
