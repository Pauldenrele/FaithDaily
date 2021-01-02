package com.bibleapp.faithdaily.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.bibleapp.faithdaily.MainApplication
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.repository.MainRepo
import com.bibleapp.faithdaily.util.DataState
import kotlinx.coroutines.launch

class MainViewModel(
    app: Application,
    val mainRepository: MainRepo
) : AndroidViewModel(app) {

    val day:Int = 0


    fun getFaithDail(day: Int):LiveData<DataState<FaithDailyResponse>>{
        return mainRepository.getFaithDaily(day).asLiveData()
    }


    fun getFirebaseImage() = liveData{
        emit(mainRepository.getFirebaseDetails().asLiveData())
    }



    fun getFaithDai(day:Int) = liveData{
        emit(mainRepository.getFaithDaily(day).asLiveData())
    }


    fun saveArticle(article: FaithDailyResponse) = viewModelScope.launch {
        mainRepository.upsert(article)
    }

   fun savFav(id:Int) =viewModelScope.launch {
       mainRepository.addFav(id)
   }

    fun delFav(id:Int) =viewModelScope.launch {
        mainRepository.delFav(id)
    }

    fun getSavedNews() = mainRepository.getfaithDetails()

    fun delete(faithDailyResponse: FaithDailyResponse) = viewModelScope.launch {
        mainRepository.deleteFav(faithDailyResponse)
    }

    fun getSavFav() = mainRepository.getFav()

    /* private suspend fun safeHomeCall(day:Int) {
        if (hasInternetConnection()) {
            val response = mainRepository.getDailyResp(day)
        } else {
        }

        try {
            if (hasInternetConnection()) {
                val response = mainRepository.getDailyResp(day)
            } else {
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->      else ->  }
        }
    }

   */ private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MainApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}