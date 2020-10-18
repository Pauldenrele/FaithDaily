package com.bibleapp.faithdaily


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bibleapp.faithdaily.R
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bibleapp.faithdaily.db.FaithDailyDatabase
import com.bibleapp.faithdaily.repository.MainRepo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainRepository = MainRepo(FaithDailyDatabase(this))
        val viewModelProviderFactory = MainViewModelProviderFactory(mainRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        bottomNavigationView.setupWithNavController(NavHostFragment.findNavController())


    }
}