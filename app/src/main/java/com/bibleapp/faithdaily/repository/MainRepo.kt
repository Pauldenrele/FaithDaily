package com.bibleapp.faithdaily.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.api.RetrofitInstance
import com.bibleapp.faithdaily.db.FaithDailyDatabase
import com.bibleapp.faithdaily.util.DataState
import com.bibleapp.faithdaily.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class MainRepo(
    val db: FaithDailyDatabase

) {


    //network
    suspend fun getDailyResp(day: Int) =
        RetrofitInstance.api.getDailyResponse(day)

    suspend fun upsert(faithDaily: FaithDailyResponse) = db.getfaithdailyDao().insert(faithDaily)

    //database
    fun getfaithDetails(day: Int) =

        db.getfaithdailyDao().getFaithDailyById(day)

    fun getFaithDaily(day: Int): Flow<DataState<FaithDailyResponse>> = flow {
        emit(DataState.Loading)
        val cache = db.getfaithdailyDao().getFaithDailyById(day)

        if (cache == null) {
            val result = RetrofitInstance.api.getDailyResponse(day)


            if( result.isSuccessful) {
                db.getfaithdailyDao().insert(result.body()!!)
                emit(DataState.Success(result.body()!!))

            }
        }else {
            emit(DataState.Success(cache))

        }

    }.catch { e ->
        Timber.e(e)
        emit(DataState.Error(null , "Failed to load"))
    }.flowOn(Dispatchers.IO)


}