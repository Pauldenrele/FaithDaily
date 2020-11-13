package com.bibleapp.faithdaily.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibleapp.faithdaily.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        val getDateNum: String? = intent.getStringExtra("Key")

        tvDetails.text = getDateNum


    }
}