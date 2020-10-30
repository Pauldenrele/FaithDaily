package com.bibleapp.faithdaily.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "faithdaily"
)
data class FaithDailyResponse(
    @SerializedName("bible_verse")
    val bible_verse: String,
    @SerializedName("daily_message")
    var daily_message: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    @PrimaryKey(autoGenerate = true)
    val day: Int,
    @SerializedName("sub_title")
    val sub_title: String,
    @SerializedName("title")
    val title: String
)