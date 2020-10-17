package com.bibleapp.faithdaily

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "faithdaily"
)
data class FaithDailyResponse(
    @PrimaryKey(autoGenerate = true)
    val bible_verse: String,
    val daily_message: String,
    val date: String,
    val day: Int,
    val sub_title: String,
    val title: String
)