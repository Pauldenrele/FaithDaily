package com.bibleapp.faithdaily

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bibleapp.faithdaily.model.FaithDailyResponse

@Dao
interface faithDailyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(faithdailyresponse: FaithDailyResponse)

    @Query("SELECT * FROM faithdaily ")
    fun getAllDetails(): LiveData<List<FaithDailyResponse>>


    @Query("SELECT * FROM faithdaily WHERE day=:id")
   fun getFaithDailyById(id:Int):FaithDailyResponse?

}