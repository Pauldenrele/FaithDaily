package com.bibleapp.faithdaily

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.repository.MainRepo
import com.bibleapp.faithdaily.util.Resource
import kotlinx.coroutines.launch

import retrofit2.Response
import java.io.IOException

class MainViewModel(
    app: Application,
    val mainRepository: MainRepo
) : AndroidViewModel(app) {

    val day:Int = 0
    val faithDailyhome: MutableLiveData<Resource<FaithDailyResponse>> = MutableLiveData()

    init {
        getDailyHome(day)
    }

    fun getDailyHome(day: Int) = viewModelScope.launch {
        safeHomeCall(day)

    }

    private fun handleDailyHomeResponse(response: Response<FaithDailyResponse>): Resource<FaithDailyResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveDetail(faithDailyDetails: FaithDailyResponse) = viewModelScope.launch {
        mainRepository.upsert(faithDailyDetails)
    }

    fun getDetails() = mainRepository.getfaithDetails()


    private suspend fun safeHomeCall(day:Int) {
        faithDailyhome.postValue(Resource.Loading())
        if (hasInternetConnection()) {
            val response = mainRepository.getDailyResp(day)
            faithDailyhome.postValue(handleDailyHomeResponse(response))
        } else {
            faithDailyhome.postValue(Resource.Error("No internet connection"))
        }

        try {
            if (hasInternetConnection()) {
                val response = mainRepository.getDailyResp(day)
                faithDailyhome.postValue(handleDailyHomeResponse(response))
            } else {
                faithDailyhome.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> faithDailyhome.postValue(Resource.Error("Network Failure"))
                else -> faithDailyhome.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
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