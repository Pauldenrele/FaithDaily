package com.bibleapp.faithdaily.api

import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.model.FirebaseImageResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FaithDailyApi {

    @GET("{day}")
    suspend fun getDailyResponse(@Path("day") day: Int): Response<FaithDailyResponse>


    @GET("/json")
    suspend fun getImageResponse(): Response<FirebaseImageResp>

}