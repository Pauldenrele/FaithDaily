package com.bibleapp.faithdaily

data class FaithDailyResponse(
    val bible_verse: String,
    val daily_message: String,
    val date: String,
    val day: Int,
    val sub_title: String,
    val title: String
)