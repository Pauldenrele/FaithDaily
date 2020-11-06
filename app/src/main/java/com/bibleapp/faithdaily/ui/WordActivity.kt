package com.bibleapp.faithdaily.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bibleapp.faithdaily.MainViewModel
import com.bibleapp.faithdaily.MainViewModelProviderFactory
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.adapter.FaithDailyAdapter
import com.bibleapp.faithdaily.db.FaithDailyDatabase
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.repository.MainRepo
import com.bibleapp.faithdaily.util.DataState
import com.bibleapp.faithdaily.util.Resource
import kotlinx.android.synthetic.main.activity_word.*
import kotlinx.coroutines.CoroutineScope

class WordActivity : AppCompatActivity() {
    lateinit var postAdapter: FaithDailyAdapter
    val TAG = "WordFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)


        val getDateNum: Int = intent.getIntExtra("keyIdentifier", 0)

        getBibleDetails(getDateNum)

    }

    private fun getBibleDetails(day: Int) {

        val mainRepository = MainRepo(FaithDailyDatabase(this))

        val viewModel: MainViewModel =
            ViewModelProviders.of(
                this,
                MainViewModelProviderFactory(this.application, mainRepository)
            )
                .get(
                    MainViewModel::class.java
                )

        viewModel.getFaithDail(day)?.observe(this, Observer {

            when (it) {
                is DataState.Success -> {
                    setupRecyclerView(listOf(it.data))
                    hideProgressBar()
                }
                is DataState.Loading -> showProgressBar()
                is DataState.Error -> hideProgressBar()
            }
        })


    }


    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false

    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true

    }


    var isLoading = false


    private fun setupRecyclerView(
        response: List<FaithDailyResponse>
    ) {
        postAdapter = FaithDailyAdapter(response)
        wordRecycler.apply {

            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
            //  addOnScrollListener(this.scrollListener)

        }
        postAdapter.notifyDataSetChanged()
    }


}