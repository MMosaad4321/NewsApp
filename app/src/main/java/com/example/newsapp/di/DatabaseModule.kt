package com.example.newsapp.di

import androidx.room.Room
import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.Utilities.DATABASE_NAME
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.NewsDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module


val databaseModule = module {
    single {
        synchronized(Any()) {
            Room.databaseBuilder(
                androidContext(),
                NewsDB::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries()
                .build()
        }
    }

    single {
        get<NewsDB>().articleDao
    } bind ArticleDao::class
}