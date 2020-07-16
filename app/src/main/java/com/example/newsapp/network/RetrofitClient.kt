package com.example.newsapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var retrofit: Retrofit? = null


    fun getClient() :Retrofit{
        if(retrofit ==null)
        retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")

            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit!!
    }
}