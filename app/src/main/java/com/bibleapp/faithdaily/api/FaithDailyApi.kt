package com.bibleapp.faithdaily.api

import com.bibleapp.faithdaily.model.FaithDailyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FaithDailyApi {

    @GET("{day}")
    suspend fun getDailyResponse(@Path("day") day: Int): Response<FaithDailyResponse>

}