package com.bibleapp.faithdaily.api

import com.bibleapp.faithdaily.FaithDailyResponse
import com.bibleapp.faithdaily.homeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FaithDailyApi {

    @GET("{day}")
    suspend fun getDailyResponse(@Path("day") day: Int): Response<FaithDailyResponse>

}