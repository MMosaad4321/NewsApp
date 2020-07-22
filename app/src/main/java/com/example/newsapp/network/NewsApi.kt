package com.example.newsapp.network

import com.example.newsapp.models.Article
import com.example.newsapp.models.Articles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApi {
    @GET("v2/top-headlines")
    fun getHeadlines(
        @Query("category") category: String?,
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?,
        @Query("page") page:Int =1
    ): Call<Articles>


    @GET("v2/top-headlines")
    fun getHeadlinesBySource(
        @Query("sources") id: String?,
        @Query("apiKey") apiKey: String?
    ): Call<Articles>

    @GET("v2/everything")
    fun searchNews(
        @Query("q") text: String?,
        @Query("sortBy")sortBy :String,
        @Query("apiKey") apiKey: String?
    ):Call<Articles>
}