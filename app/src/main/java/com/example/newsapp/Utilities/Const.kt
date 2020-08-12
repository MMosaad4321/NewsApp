package com.example.newsapp.Utilities


//region Intent Parameter
const val URL_KEY = "news-url"
const val DATABASE_NAME = "fav_news"


//endregion

const val FAV_TAG = "FAV"
const val NOT_FAV_TAG = "NOT-FAV"
 const val RC_SIGN_IN: Int = 1

enum class Api_Environment  {

    development {
        override fun getApiUrl(): String = "https://newsapi.org/"
    },

    prouction {
        override fun getApiUrl(): String  = ""
    };

    abstract fun getApiUrl() : String
}