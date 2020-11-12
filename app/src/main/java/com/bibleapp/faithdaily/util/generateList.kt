package com.bibleapp.faithdaily.util

fun generateList():List<Int>{
    val num = mutableListOf<Int>()
    for(i in 1..365){
        num.add(i)
    }
    return num
}