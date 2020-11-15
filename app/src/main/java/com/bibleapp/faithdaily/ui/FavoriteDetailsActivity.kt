package com.bibleapp.faithdaily.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bibleapp.faithdaily.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_favorite_details.*

class FavoriteDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_details)

        val getDetails: String? = intent.getStringExtra("KeyFav")
        val getTitle: String? = intent.getStringExtra("KeyTitleFav")
        val getVerse: String? = intent.getStringExtra("KeyVerseFav")

        tvDetailsFav.text = getDetails
        tvTitleFav.text = getTitle
        tvVerseFav.text = getVerse

    }
}