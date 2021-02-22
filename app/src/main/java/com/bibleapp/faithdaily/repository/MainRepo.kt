package com.bibleapp.faithdaily.repository

import com.bibleapp.faithdaily.api.FirebaseRetrofitInstance
import com.bibleapp.faithdaily.api.RetrofitInstance
import com.bibleapp.faithdaily.db.FaithDailyDatabase
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.model.FirebaseImageResp
import com.bibleapp.faithdaily.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class MainRepo(
    val db: FaithDailyDatabase

) {


    fun getFav() = db.getfaithdailyDao().getsavDetails()

    suspend fun deleteFav(faithDaily: FaithDailyResponse) = db.getfaithdailyDao().delete(faithDaily)
    suspend fun addFav(id: Int) = db.getfaithdailyDao().setFavorite(id)
    suspend fun delFav(id: Int) = db.getfaithdailyDao().setFalseFavorite(id)

    suspend fun upsert(faithDaily: FaithDailyResponse) = db.getfaithdailyDao().insert(faithDaily)

    //database
    fun getfaithDetails() =
        db.getfaithdailyDao().getAllDetails()

    fun getFaithDaily(day: Int): Flow<DataState<FaithDailyResponse>> = flow {
        emit(DataState.Loading)
        val cache = db.getfaithdailyDao().getFaithDailyById(day)

        if (cache == null) {
            val result = RetrofitInstance.api.getDailyResponse(day)


            if (result.isSuccessful) {
                db.getfaithdailyDao().insert(result.body()!!)
                emit(DataState.Success(result.body()!!))

            }
        } else {
            emit(DataState.Success(cache))

        }

    }.catch { e ->
        Timber.e(e)
        emit(DataState.Error(null, "Failed to load"))
    }.flowOn(Dispatchers.IO)


    fun getFirebaseDetails(): Flow<DataState<FirebaseImageResp>> = flow {
        emit(DataState.Loading)

        val result = FirebaseRetrofitInstance.api.getImageResponse()


        if (result.isSuccessful) {
            emit(DataState.Success(result.body()!!))
        }


    }.catch { e ->
        Timber.e(e)
        emit(DataState.Error(null, "Failed to load"))
    }.flowOn(Dispatchers.IO)

}