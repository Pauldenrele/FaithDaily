package com.bibleapp.faithdaily

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

      //  splash_logo.setImageResource(R.drawable.splash_screen_logo)

        val SPLASH_TIME_OUT = 2000
        val homeIntent = Intent(this@SplashScreen, MainActivity::class.java)
        Handler().postDelayed({
            //Do some stuff here, like implement deep linking
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

}