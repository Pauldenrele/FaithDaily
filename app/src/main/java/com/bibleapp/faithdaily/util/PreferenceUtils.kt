package com.bibleapp.faithdaily.util

import android.content.Context
import android.content.SharedPreferences
import com.bibleapp.faithdaily.R

class PreferenceUtils constructor(val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            "context", Context.MODE_PRIVATE)
    }



    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean? {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

}