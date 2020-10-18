package com.bibleapp.faithdaily.repository

import com.bibleapp.faithdaily.api.RetrofitInstance
import com.bibleapp.faithdaily.db.FaithDailyDatabase

class MainRepo(
    val db: FaithDailyDatabase
) {
    suspend fun getDailyResp() =
        RetrofitInstance.api.getDailyResponse()
}