package com.bibleapp.faithdaily.util

import java.lang.Exception

sealed class DataState <out R>{
    data class Success<out T>(val data:T):DataState<T>()
    data class Error(val exception: Exception? , val message:String):DataState<Nothing>()
    object Loading:DataState<Nothing>()

    override fun toString(): String {
        return when (this){
            is Success<*> -> "Sucess[data=$data]"
            is Error -> "Error[exception =$exception]"
            Loading -> "Loading"
        }

    }
}