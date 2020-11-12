package com.bibleapp.faithdaily.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bibleapp.faithdaily.faithDailyDao
import com.bibleapp.faithdaily.model.FaithDailyResponse

@Database(
    entities = [FaithDailyResponse::class],
    version = 1 ,  exportSchema = false
)
abstract class FaithDailyDatabase : RoomDatabase() {

    abstract fun getfaithdailyDao(): faithDailyDao


    companion object {
        @Volatile
        private var instance: FaithDailyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FaithDailyDatabase::class.java,
                "faithdaily_db.db"
            ).fallbackToDestructiveMigration().build()
    }
}