package com.bibleapp.faithdaily.model

import androidx.room.ColumnInfo
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
    @SerializedName("isFav")
    val isFav: Boolean,
    @SerializedName("day")
    @PrimaryKey
    val day: Int,
    @SerializedName("title")
    val title: String
)