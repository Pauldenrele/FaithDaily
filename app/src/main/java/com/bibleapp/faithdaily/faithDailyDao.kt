package com.bibleapp.faithdaily

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bibleapp.faithdaily.model.FaithDailyResponse

@Dao
interface faithDailyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(faithdailyresponse: FaithDailyResponse)

    @Query("SELECT * FROM faithdaily ")
    fun getAllDetails(): LiveData<List<FaithDailyResponse>>


    @Query("UPDATE faithdaily SET isFav = 1 WHERE day=:id")
    suspend fun setFavorite(id: Int)

    @Query("UPDATE faithdaily SET isFav = 0 WHERE day=:id")
    suspend fun setFalseFavorite(id: Int)


    @Query("SELECT * FROM faithdaily WHERE isFav = 1")
    fun getsavDetails(): LiveData<MutableList<FaithDailyResponse>>


    @Query("SELECT * FROM faithdaily WHERE day=:id")
    fun getFaithDailyById(id: Int): FaithDailyResponse?


    @Delete
    suspend fun delete(faithdailyresponse: FaithDailyResponse)

}