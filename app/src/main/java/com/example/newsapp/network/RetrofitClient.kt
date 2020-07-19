package com.example.newsapp.network

import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.Utilities.Api_Environment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var retrofit: Retrofit? = null


    fun getClient() :Retrofit{
        if(retrofit ==null)
        retrofit = Retrofit.Builder()
            .baseUrl(ApplicationDelegate.environment)

            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit!!
    }
}