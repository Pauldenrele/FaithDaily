package com.bibleapp.faithdaily.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bibleapp.faithdaily.FaithDailyResponse

@Dao
interface FaithDailyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(faithdailyresponse: FaithDailyResponse): Long

    @Query("SELECT * FROM faithdaily")
    fun getAllDetails(): LiveData<List<FaithDailyResponse>>

}