package com.bibleapp.faithdaily.ui.HomeAcitvites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibleapp.faithdaily.R
import kotlinx.android.synthetic.main.activity_today_.*
import kotlinx.android.synthetic.main.activity_today_.tvDetailsToday
import kotlinx.android.synthetic.main.activity_today_.tvTitleToday
import kotlinx.android.synthetic.main.activity_today_.tvVerseToday
import kotlinx.android.synthetic.main.activity_yesterday.*

class YesterdayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yesterday)

        val getDetails: String? = intent.getStringExtra("yesterday")
        val getTitle: String? = intent.getStringExtra("yesterdayTitle")
        val getVerse: String? = intent.getStringExtra("yesterdayVerse")

        tvDetailsYest.text = getDetails
        tvTitleYest.text = getTitle
        tvVerseYest.text = getVerse

    }
}