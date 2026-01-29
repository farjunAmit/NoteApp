package com.example.noteapp.data

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromDate(date: LocalDate?): String? =
        date?.toString()

    @TypeConverter
    fun toDate(dateString: String?): LocalDate? =
        dateString?.let(LocalDate::parse)
}