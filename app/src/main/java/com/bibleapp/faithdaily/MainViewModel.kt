package com.bibleapp.faithdaily

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibleapp.faithdaily.repository.MainRepo
import com.bibleapp.faithdaily.util.Resource
import kotlinx.coroutines.launch

import retrofit2.Response

class MainViewModel(
    val mainRepository: MainRepo
) : ViewModel() {

    val faithDailyhome: MutableLiveData<Resource<homeResponse>> = MutableLiveData()

    init {
        getDailyHome()
    }

    fun getDailyHome() = viewModelScope.launch {
        faithDailyhome.postValue(Resource.Loading())
        val response = mainRepository.getDailyResp()
        faithDailyhome.postValue(handleDailyHomeResponse(response))
    }

    private fun handleDailyHomeResponse(response: Response<homeResponse>) : Resource<homeResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}