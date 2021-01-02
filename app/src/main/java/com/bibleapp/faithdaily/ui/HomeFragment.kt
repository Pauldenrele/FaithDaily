package com.bibleapp.faithdaily.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bibleapp.faithdaily.MainActivity
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.adapter.GetDetailsAdapter
import com.bibleapp.faithdaily.adapter.ScrollCustomAdapter
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.model.ImageModel
import com.bibleapp.faithdaily.util.DataState
import com.bibleapp.faithdaily.util.generateList
import com.bibleapp.faithdaily.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_word.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.paginationProgressBar
import java.time.LocalDate
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel: MainViewModel
    lateinit var postAdapter: GetDetailsAdapter
    private lateinit var adapter: ScrollCustomAdapter

    lateinit var viewPager: ViewPager2
    //lateinit var postAdapter: FaithDailyAdapter

    //    abstract val compositePageTransformer: CompositePageTransformer
    val TAG = "HomeFragment"

    private var imageModelArrayList: ArrayList<ImageModel>? = null
    // private var adapter: ImageAdapter? = null
    //handle scroll count

    var scrollCount: Int = 0

    private lateinit var layoutManager: LinearLayoutManager

    //handler for run auto scroll thread
    internal val handler = Handler()
    val displayMetrics = DisplayMetrics()


    private val myImageList = intArrayOf(
        R.drawable.image1,
        R.drawable.faith_speak,
        R.drawable.faith_acts,
        R.drawable.faith_changes_situation,
        R.drawable.faith_honours_god,
        R.drawable.faith_pleases_god,
        R.drawable.faith_rejoices,
        R.drawable.faith_uplifts,
        R.drawable.faith_is_powerful,
        R.drawable.faith_builds,
        R.drawable.faith_is_patient
    )
    private val myImageNameList =
        arrayOf(
            "Faith Overcomes",
            "Faith Speaks",
            "Faith Acts",
            "Faith Changes Situation",
            "Faith Honours God",
            "Faith Pleases God",
            "Faith Rejoices",
            "Faith Uplifts",
            "Faith is Powerful",
            "Faith Builds",
            "Faith is Patient"
        )


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        val calendar = Calendar.getInstance()
        val dayofYear = calendar[Calendar.DAY_OF_YEAR]


        val currentday = LocalDate.now()
        val dayofwEEK = currentday.dayOfWeek


        viewModel.getFirebaseImage().observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {
                        Toast.makeText(
                            context, "Image ${it.data}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                        Toast.makeText(
                            context, "Image load",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)
                        Toast.makeText(
                            context, "Image error ${it.exception}",
                            Toast.LENGTH_SHORT
                        ).show()


                    }

                }
            })
        })


        viewModel.getFaithDai(dayofYear - 1).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {
                        card_today.visibility = View.VISIBLE
                        yesterdayTitle.text = it.data.title
                        yesterdayVerse.text = it.data.bible_verse
                        yesterdayDescription.text = it.data.daily_message
                        hideProgressBar()
                        //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }

            })
        })

        viewModel.getFaithDai(dayofYear).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {

                        card_view_yest.visibility = View.VISIBLE
                        //    Toast.makeText(activity , "${it.data.title}" , Toast.LENGTH_LONG).show()
                        todayTitle.text = it.data.title
                        todayVerse.text = it.data.bible_verse
                        todayDesc.text = it.data.daily_message
                        hideProgressBar()
                        //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }

            })
        })


        viewModel.getFaithDai(dayofYear - 2).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {
                        card_day1.visibility = View.VISIBLE
                        Day1day.text = "${dayofwEEK - 2}"
                        Day1Title.text = it.data.title
                        Day1Verse.text = it.data.bible_verse
                        Day1Description.text = it.data.daily_message
                        hideProgressBar()
                        //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }

            })
        })


        viewModel.getFaithDai(dayofYear - 3).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {
                        card_view_day2.visibility = View.VISIBLE
                        Day2day.text = "${dayofwEEK - 3}"
                        Day2Title.text = it.data.title
                        Day2Verse.text = it.data.bible_verse
                        Day2Description.text = it.data.daily_message
                        hideProgressBar()
                        //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }

            })
        })


        viewModel.getFaithDai(dayofYear - 4).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {
                        card_view_day3.visibility = View.VISIBLE
                        Day3day.text = "${dayofwEEK - 4}"
                        Day3Title.text = it.data.title
                        Day3Verse.text = it.data.bible_verse
                        Day3Description.text = it.data.daily_message
                        hideProgressBar()
                        //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }

            })
        })


        viewModel.getFaithDai(dayofYear - 5).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {
                        card_view_day4.visibility = View.VISIBLE
                        Day4day.text = "${dayofwEEK - 5}"
                        Day4Title.text = it.data.title
                        Day4Verse.text = it.data.bible_verse
                        Day4Description.text = it.data.daily_message
                        hideProgressBar()
                        //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }

            })
        })

        viewModel.getFaithDai(dayofYear - 6).observe(viewLifecycleOwner, Observer {
            it.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is DataState.Success -> {
                        card_view_day5.visibility = View.VISIBLE
                        Day5day.text = "${dayofwEEK - 6}"
                        Day5Title.text = it.data.title
                        Day5Verse.text = it.data.bible_verse
                        Day5Description.text = it.data.daily_message
                        hideProgressBar()
                        //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }

            })
        })


/*
        for ( i in 1..3 ){
            count +=1
            val day = i + count

            viewModel.getFaithDail(day).observe(viewLifecycleOwner, Observer {

                when (it) {
                    is DataState.Success -> {
                        setupRecyclerView(listOf(it.data))
                        hideProgressBar()
                      //  hideRetryButton()
                    }
                    is DataState.Loading -> {
                        showProgressBar()
                        //hideRetryButton()
                    }
                    is DataState.Error -> {
                        hideProgressBar()
                        //  showRetryButton(i)

                    }
                }
            })

        }
*/


        /*viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            setupRecyclerView(articles)
            if (postAdapter!!.itemCount == 0) {
                txtEmpty.visibility = View.VISIBLE
                txtEmpty.setOnClickListener {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_HomeFragment_to_calenderFragment)

                }
            }

            postAdapter.setOnItemClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("Key", it.daily_message)
                intent.putExtra("KeyTitle", it.title)
                intent.putExtra("KeyVerse", it.bible_verse)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity as MainActivity,
                    (tvDescription as View?)!!, "profile"
                )
                startActivity(intent, options.toBundle())

            }
        })*/

        //   getBibleDetails()

        imageModelArrayList = eatFruits()
        initLayoutManager()
        // adapter = imageModelArrayList?.let { ImageAdapter(activity, it) }

        /*recycler.setAdapter(adapter)
        recycler.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )*/

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


    private fun setupRecyclerView(
        response: List<FaithDailyResponse>
    ) {
        val randlist = generateList()
        postAdapter = GetDetailsAdapter(response)

        // postAdapter = FaithDailyAdapter(response)


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

    private fun initLayoutManager() {

        layoutManager = object : LinearLayoutManager(context) {
            override fun smoothScrollToPosition(
                recyclerView: RecyclerView,
                state: RecyclerView.State?,
                position: Int
            ) {
                val smoothScroller = object : LinearSmoothScroller(context) {
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                        return 5.0f;
                    }
                }
                smoothScroller.targetPosition = position
                startSmoothScroll(smoothScroller)
            }
        }
        adapter = object : ScrollCustomAdapter(context!!, imageModelArrayList!!) {
            override fun load() {
                if (layoutManager.findFirstVisibleItemPosition() > 1) {
                    adapter.notifyItemMoved(0, imageModelArrayList!!.size - 1)
                }
            }
        }

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)
        recycler.setItemViewCacheSize(10)
        recycler.isDrawingCacheEnabled = true
        recycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_LOW
        recycler.adapter = adapter
        autoScroll()
    }


    private fun autoScroll() {
        scrollCount = 0;
        var speedScroll: Long = 1200;
        val runnable = object : Runnable {
            override fun run() {
                if (layoutManager.findFirstVisibleItemPosition() >= imageModelArrayList!!.size / 2) {
                    adapter.load()
                    Log.e(TAG, "run: load $scrollCount")
                }
                //  recycler.smoothScrollToPosition(scrollCount++)
                Log.e(TAG, "run: $scrollCount")
                handler.postDelayed(this, speedScroll)
            }
        }
        handler.postDelayed(runnable, speedScroll)
    }


    private fun eatFruits(): ArrayList<ImageModel>? {
        val list: ArrayList<ImageModel> = ArrayList<ImageModel>()
        for (i in 0..6) {
            val fruitModel = ImageModel()
            fruitModel.name = myImageNameList[i]
            fruitModel.image_drawable = myImageList[i]
            list.add(fruitModel)
        }
        return list
    }

    override fun onResume() {
        super.onResume()
        // getBibleDetails()
    }
}