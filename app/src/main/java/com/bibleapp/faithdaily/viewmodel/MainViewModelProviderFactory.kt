package com.bibleapp.faithdaily.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bibleapp.faithdaily.repository.MainRepo

class MainViewModelProviderFactory(
    val app: Application,
    val mainRepository: MainRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app ,mainRepository) as T
    }
}