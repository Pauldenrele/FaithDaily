package com.bibleapp.faithdaily.ui

import android.os.Bundle
import android.view.View
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
import com.bibleapp.faithdaily.util.DataState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_word.*

class WordActivity : AppCompatActivity() {
    lateinit var postAdapter: FaithDailyAdapter
    val TAG = "WordFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        val getDateNum: Int = intent.getIntExtra("keyIdentifier", 0)

        fab.setOnClickListener {

            val parentLayout = findViewById<View>(android.R.id.content)

            val snack =
                Snackbar.make(parentLayout, "Saved", Snackbar.LENGTH_LONG)
            val snackbarView = snack.view
            snackbarView.setBackgroundColor(resources.getColor(R.color.colorAccent))
            snack.show()



            saveDetails(id = getDateNum)
        }


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

        viewModel.getFaithDail(day).observe(this, Observer {

            when (it) {
                is DataState.Success -> {
                    setupRecyclerView(listOf(it.data))
                    hideProgressBar()
                    hideRetryButton()
                }
                is DataState.Loading -> {
                    showProgressBar()
                    hideRetryButton()
                }
                is DataState.Error -> {
                    hideProgressBar()
                    showRetryButton(day)

                }
            }
        })


    }


    private fun saveDetails(id: Int) {
        val mainRepository = MainRepo(FaithDailyDatabase(this))

        val viewModel: MainViewModel =
            ViewModelProviders.of(
                this,
                MainViewModelProviderFactory(this.application, mainRepository)
            ).get(
                MainViewModel::class.java
            )

        viewModel.savFav(id)


    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false

    }

    private fun hideRetryButton() {
        retryButton.visibility = View.INVISIBLE
    }

    private fun showRetryButton(day: Int) {
        val parentLayout = findViewById<View>(android.R.id.content)

        retryButton.visibility = View.VISIBLE
        val snack =
            Snackbar.make(parentLayout, "Check your Internet Connection", Snackbar.LENGTH_LONG)
        val snackbarView = snack.view
        snackbarView.setBackgroundColor(resources.getColor(R.color.colorAccent))

        snack.show()
        retryButton.setOnClickListener {
            getBibleDetails(day)
            isLoading = true

        }
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