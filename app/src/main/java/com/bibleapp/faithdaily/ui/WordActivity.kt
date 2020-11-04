package com.bibleapp.faithdaily.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bibleapp.faithdaily.MainViewModel
import com.bibleapp.faithdaily.MainViewModelProviderFactory
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.adapter.FaithDailyAdapter
import com.bibleapp.faithdaily.db.FaithDailyDatabase
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.repository.MainRepo
import com.bibleapp.faithdaily.util.Resource
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var postAdapter: FaithDailyAdapter
    val TAG = "WordFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)



        val ss: Int = intent.getIntExtra("keyIdentifier" ,0)
        Toast.makeText(
            this, "$ss",
            Toast.LENGTH_SHORT
        ).show()

         getBibleDetails(ss)


    }
    private fun getBibleDetails(day:Int) {
        val mainRepository = MainRepo(FaithDailyDatabase(this))

        val viewModel: MainViewModel =
            ViewModelProviders.of(this, MainViewModelProviderFactory(this.application, mainRepository))
                .get(
                    MainViewModel::class.java
                )

        viewModel.getDailyHome(day)

       viewModel.faithDailyhome.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data
                    setupRecyclerView(listOf(response.data!!))

                    Log.d(TAG, "Message: ${response.data}")
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
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
    var isLastPage = false
    var isScrolling = false



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