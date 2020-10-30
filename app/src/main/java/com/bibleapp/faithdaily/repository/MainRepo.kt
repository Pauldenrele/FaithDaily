package com.bibleapp.faithdaily.repository

import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.api.RetrofitInstance
import com.bibleapp.faithdaily.db.FaithDailyDatabase

class MainRepo(
    val db: FaithDailyDatabase
) {
    suspend fun getDailyResp(day: Int) =
        RetrofitInstance.api.getDailyResponse(2)

    suspend fun upsert(faithDaily: FaithDailyResponse) = db.getfaithdailyDao().insert(faithDaily)

    fun getfaithDetails() = db.getfaithdailyDao().getAllDetails()

}