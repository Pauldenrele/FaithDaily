package com.bibleapp.faithdaily.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibleapp.faithdaily.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        val getDetails: String? = intent.getStringExtra("Key")
        val getTitle: String? = intent.getStringExtra("KeyTitle")
        val getVerse: String? = intent.getStringExtra("KeyVerse")

        tvDetails.text = getDetails
        tvTitleHome.text = getTitle
        tvVerseHome.text = getVerse


    }
}