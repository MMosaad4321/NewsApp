package com.example.newsapp.network

import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.Utilities.Api_Environment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var retrofit: Retrofit? = null


    fun getClient() :Retrofit{
        if(retrofit ==null){

            /** [ adding interceptor for logging network requests in logcat] **/
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(ApplicationDelegate.environment)

                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}