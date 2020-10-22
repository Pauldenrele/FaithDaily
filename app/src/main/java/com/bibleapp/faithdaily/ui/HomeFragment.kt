package com.bibleapp.faithdaily.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
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
import kotlinx.android.synthetic.main.item_preview.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var viewModel: MainViewModel
     lateinit var postAdapter : FaithDailyAdapter


    val TAG = "HomeFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
      //  setupRecyclerView()


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
            if(shouldPaginate) {
                viewModel.getDailyHome()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView(
        response: List<FaithDailyResponse>
    ){
        postAdapter = FaithDailyAdapter(response)
        rvBreakingNews.apply {

            adapter = postAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HomeFragment.scrollListener)

        }
        postAdapter.notifyDataSetChanged()
    }
}