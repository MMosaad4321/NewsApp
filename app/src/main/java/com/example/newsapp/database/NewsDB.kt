package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDB : RoomDatabase() {
    abstract val articleDao : ArticleDao

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "fav_news"
        private var sInstance: NewsDB? = null
        fun getInstance(context: Context): NewsDB? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDB::class.java,
                        DATABASE_NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return sInstance
        }
    }
}