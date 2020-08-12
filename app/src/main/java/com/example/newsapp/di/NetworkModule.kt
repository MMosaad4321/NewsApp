package com.example.newsapp.di

import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.network.NewsApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module{


    single {
        val interceptor = HttpLoggingInterceptor()
            interceptor.level = (HttpLoggingInterceptor.Level.BODY)
        interceptor
    } bind Interceptor::class

    single {
        val client = OkHttpClient.Builder()
            .addInterceptor(get())
            .build()
        client
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(ApplicationDelegate.environment)
            .addConverterFactory(get())
            .build()
    }

    single  {
        get<Retrofit>()
            .create(NewsApi::class.java)
    }
}