package com.bibleapp.faithdaily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bibleapp.faithdaily.repository.MainRepo

class MainViewModelProviderFactory(
    val mainRepository: MainRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository) as T
    }
}