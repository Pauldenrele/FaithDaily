package com.bibleapp.faithdaily.ui.HomeAcitvites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibleapp.faithdaily.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.tvDetails
import kotlinx.android.synthetic.main.activity_details.tvTitleHome
import kotlinx.android.synthetic.main.activity_details.tvVerseHome
import kotlinx.android.synthetic.main.activity_today_.*

class TodayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_)

        val getDetails: String? = intent.getStringExtra("today")
        val getTitle: String? = intent.getStringExtra("todayTitle")
        val getVerse: String? = intent.getStringExtra("todayVerse")

        tvDetailsToday.text = getDetails
        tvTitleToday.text = getTitle
        tvVerseToday.text = getVerse

    }
}