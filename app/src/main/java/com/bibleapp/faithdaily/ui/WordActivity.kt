package com.bibleapp.faithdaily.ui

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bibleapp.faithdaily.viewmodel.MainViewModel
import com.bibleapp.faithdaily.viewmodel.MainViewModelProviderFactory
import com.bibleapp.faithdaily.R
import com.bibleapp.faithdaily.adapter.FaithDailyAdapter
import com.bibleapp.faithdaily.db.FaithDailyDatabase
import com.bibleapp.faithdaily.model.FaithDailyResponse
import com.bibleapp.faithdaily.repository.MainRepo
import com.bibleapp.faithdaily.util.DataState
import com.bibleapp.faithdaily.util.PreferenceUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_word.*
import java.util.*

class WordActivity : AppCompatActivity() {
    lateinit var postAdapter: FaithDailyAdapter
    val TAG = "WordFragment"

    lateinit var mTTS: TextToSpeech


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val getDatNum: Int = intent.getIntExtra("keyIdentifier", 0)

        val mainRepository = MainRepo(FaithDailyDatabase(this))

        val viewModel: MainViewModel =
            ViewModelProviders.of(
                this,
                MainViewModelProviderFactory(this.application, mainRepository)
            )
                .get(
                    MainViewModel::class.java
                )


        val getDateNum: Int = intent.getIntExtra("keyIdentifier", 0)
        val parentLayout = findViewById<View>(android.R.id.content)

        val id = item.getItemId()

        return when (id) {
            R.id.action_one -> {
                saveDetails(id = getDateNum)
                val snack =
                    Snackbar.make(parentLayout, "Saved", Snackbar.LENGTH_LONG)
                val snackbarView = snack.view
                snackbarView.setBackgroundColor(resources.getColor(R.color.colorAccent))
                snack.show()

                true
            }
            R.id.action_two -> {
                val toSpeak = "My name is John"
                if (toSpeak == "") {
                    //if there is no text in edit text
                    Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
                } else {

                    viewModel.getFaithDail(getDatNum).observe(this, Observer {

                        when (it) {
                            is DataState.Success -> {
                                mTTS = TextToSpeech(
                                    applicationContext,
                                    TextToSpeech.OnInitListener { status ->


                                        if (status != TextToSpeech.ERROR) {
                                            //if there is no error then set language
                                            mTTS.language = Locale.UK

                                            mTTS.speak(
                                                it.data.daily_message,
                                                TextToSpeech.QUEUE_FLUSH,
                                                null
                                            )

                                        }
                                    })

                            }
                            is DataState.Loading -> {
                            }
                            is DataState.Error -> {

                            }
                        }
                    })

                }


                true

            }
            else -> super.onOptionsItemSelected(item)

        }

    }

    override fun onPause() {
        super.onPause()

        mTTS.shutdown()
        mTTS.stop()
        mTTS = TextToSpeech(
            applicationContext,
            TextToSpeech.OnInitListener { status ->


                if (status != TextToSpeech.ERROR) {
                    //if there is no error then set language
                    mTTS.language = Locale.UK

                    if (mTTS.isSpeaking) {
                        //if speaking then stop
                        mTTS.stop()
                        mTTS.shutdown()
                    }

                }
            })


    }

    override fun onStop() {
        super.onStop()

        mTTS.shutdown()
        mTTS.stop()

        mTTS = TextToSpeech(
            applicationContext,
            TextToSpeech.OnInitListener { status ->


                if (status != TextToSpeech.ERROR) {
                    //if there is no error then set language
                    mTTS.language = Locale.UK

                    if (mTTS.isSpeaking) {
                        //if speaking then stop
                        mTTS.stop()
                        mTTS.shutdown()
                    }

                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mTTS.isSpeaking) {
            //if speaking then stop
            mTTS.stop()
            //mTTS.shutdown()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        val speech = menu.findItem(R.id.action_two)
        return true
    }


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