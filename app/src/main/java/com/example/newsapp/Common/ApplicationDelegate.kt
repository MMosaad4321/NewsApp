package com.example.newsapp.Common

import android.app.Application
import android.content.Context
import com.example.newsapp.Utilities.Api_Environment
import com.example.newsapp.di.appModule
import com.example.newsapp.di.databaseModule
import com.example.newsapp.di.networkModule
import com.example.newsapp.di.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class ApplicationDelegate : Application() {

    companion object {
        val environment = Api_Environment.development.getApiUrl()
        lateinit var mInstance : Context

    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(baseContext)
            modules(listOf(appModule , networkModule, databaseModule, repoModule))

        }
        mInstance = this
    }
}