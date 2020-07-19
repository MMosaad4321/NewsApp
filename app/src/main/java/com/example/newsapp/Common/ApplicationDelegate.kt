package com.example.newsapp.Common

import android.app.Application
import android.content.Context
import com.example.newsapp.Utilities.Api_Environment
import com.example.newsapp.database.NewsDB

class ApplicationDelegate : Application() {

    companion object {
        val environment = Api_Environment.development.getApiUrl()
        lateinit var mDb : NewsDB

    }

    override fun onCreate() {
        super.onCreate()
        mDb  = NewsDB.getInstance(this)!!
    }
}