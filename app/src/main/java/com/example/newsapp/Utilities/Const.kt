package com.example.newsapp.Utilities


//region Intent Parameter
const val URL_KEY = "news-url"

//endregion

const val FAV_TAG = "FAV"
const val NOT_FAV_TAG = "NOT-FAV"

enum class Api_Environment  {

    development {
        override fun getApiUrl(): String = "https://newsapi.org/"
    },

    prouction {
        override fun getApiUrl(): String  = ""
    };

    abstract fun getApiUrl() : String
}