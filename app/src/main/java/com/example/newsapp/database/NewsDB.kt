package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.Article
import org.koin.core.KoinComponent
import org.koin.core.context.KoinContextHandler.get
import org.koin.core.inject

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDB : RoomDatabase() {
    abstract val articleDao : ArticleDao
}