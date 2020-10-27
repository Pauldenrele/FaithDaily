package com.bibleapp.faithdaily.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.FaithDailyResponse
import com.bibleapp.faithdaily.MainActivity
import com.bibleapp.faithdaily.MainViewModel
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.adapter.FaithDailyAdapter
import com.bibleapp.faithdaily.util.Constants.Companion.QUERY_PAGE_SIZE
import com.bibleapp.faithdaily.util.Resource
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var viewModel: MainViewModel
    lateinit var postAdapter: FaithDailyAdapter


    val TAG = "HomeFragment"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel


        setupDate()
        viewModel.faithDailyhome.observe(viewLifecycleOwner, Observer { response ->
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

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getDailyHome()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView(
        response: List<FaithDailyResponse>
    ) {
        postAdapter = FaithDailyAdapter(response)
        rvBreakingNews.apply {

            adapter = postAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HomeFragment.scrollListener)

        }
        postAdapter.notifyDataSetChanged()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDate() {
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            Toast.makeText(activity, "Good Morning", Toast.LENGTH_SHORT).show()
            img_day.setImageResource(R.drawable.sunrise32px)
            txtDay.text = "Good\n Morning"
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Toast.makeText(activity, "Good Afternoon", Toast.LENGTH_SHORT).show()
            img_day.setImageResource(R.drawable.sun32px)

            txtDay.text = "Good \nAfternoon"
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Toast.makeText(activity, "Good Evening", Toast.LENGTH_SHORT).show()
            img_day.setImageResource(R.drawable.moon32px)

            txtDay.text = "Good \nEvening"

        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            Toast.makeText(activity, "Good Night", Toast.LENGTH_SHORT).show()
            img_day.setImageResource(R.drawable.moon32px)

            txtDay.text = "Good\nEvening"
        }

        val current = LocalDate.now()
        txtDate.text = "${
            current.dayOfWeek.toString().substring(0, 1).toUpperCase() +
                    current.dayOfWeek.toString().substring(1).toLowerCase()
        } ,${current.dayOfMonth} ${ current.month.toString().substring(0, 1).toUpperCase() +
                current.month.toString().substring(1).toLowerCase()}"


    }
}