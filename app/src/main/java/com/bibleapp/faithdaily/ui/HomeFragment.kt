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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bibleapp.faithdaily.*
import com.bibleapp.faithdaily.adapter.FaithDailyAdapter
import com.bibleapp.faithdaily.adapter.GetDetailsAdapter
import com.bibleapp.faithdaily.adapter.ImageAdapter
import com.bibleapp.faithdaily.db.FaithDailyDatabase
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.model.ImageModel
import com.bibleapp.faithdaily.repository.MainRepo
import com.bibleapp.faithdaily.util.Constants.Companion.QUERY_PAGE_SIZE
import com.bibleapp.faithdaily.util.DataState
import com.bibleapp.faithdaily.util.Resource
import com.bibleapp.faithdaily.util.generateList
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_word.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.paginationProgressBar
import java.lang.Math.random
import java.time.LocalDate
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel: MainViewModel
    lateinit var postAdapter: GetDetailsAdapter

    val TAG = "HomeFragment"

    private var imageModelArrayList: ArrayList<ImageModel>? = null
    private var adapter: ImageAdapter? = null

    private val myImageList = intArrayOf(
        R.drawable.prayerlanguage,
        R.drawable.worship,
        R.drawable.praying,
        R.drawable.worship,
        R.drawable.prayerlanguage,
        R.drawable.praying,
        R.drawable.worship
    )
    private val myImageNameList =
        arrayOf("Apple", "Mango", "Strawberry", "Pineapple", "Orange", "Blueberry", "Watermelon")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel



        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            setupRecyclerView(articles)
        })

        //   getBibleDetails()

        imageModelArrayList = eatFruits()
        adapter = imageModelArrayList?.let { ImageAdapter(activity, it) }
        recycler.setAdapter(adapter)
        recycler.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        setupDate()

    }




    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false

    }

    private fun hideRetryButton() {
        retryButton.visibility = View.INVISIBLE
    }

    private fun showRetryButton() {

      //  retryButton.visibility = View.VISIBLE
     /*   retryButton.setOnClickListener {
            getBibleDetails()
            isLoading = true

        }*/
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
             //   viewModel.getDailyHome()
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
        val randlist = generateList()
        postAdapter = GetDetailsAdapter(response)


        rvDetailsSaved.apply {

            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
            //  addOnScrollListener(this.scrollListener)

        }
        postAdapter.notifyDataSetChanged()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDate() {
        val c: Calendar = Calendar.getInstance()
        val timeOfDay: Int = c.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            img_day.setImageResource(R.drawable.sunrise32px)
            txtDay.text = "Good\nMorning"
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            img_day.setImageResource(R.drawable.sun32px)
            txtDay.text = "Good\nAfternoon"
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            img_day.setImageResource(R.drawable.moon32px)
            txtDay.text = "Good\nEvening"

        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            img_day.setImageResource(R.drawable.moon32px)
            txtDay.text = "Good\nEvening"
        }

        val current = LocalDate.now()
        txtDate.text = "${
            current.dayOfWeek.toString().substring(0, 1).toUpperCase() +
                    current.dayOfWeek.toString().substring(1).toLowerCase()
        } ,${current.dayOfMonth} ${
            current.month.toString().substring(0, 1).toUpperCase() +
                    current.month.toString().substring(1).toLowerCase()
        }"


    }
    private fun eatFruits(): ArrayList<ImageModel>? {
        val list: ArrayList<ImageModel> = ArrayList<ImageModel>()
        for (i in 0..6) {
            val fruitModel = ImageModel()
            fruitModel.name = myImageNameList[i]
            fruitModel.image_drawable=myImageList[i]
            list.add(fruitModel)
        }
        return list
    }

    override fun onResume() {
        super.onResume()
       // getBibleDetails()
    }
}