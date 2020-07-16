package com.example.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.models.Article
import com.example.newsapp.models.Articles
import com.example.newsapp.network.NewsApi
import com.example.newsapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    var articlesList  = MutableLiveData<List<Article>>()
    fun getNews(){

        var newsApi = RetrofitClient.getClient().create(NewsApi::class.java)
        val call = newsApi.getHeadlines("business","eg","86cf320fbd4e404db937cb915aef1cb3")
        call.enqueue(object : Callback<Articles> {
            override fun onFailure(call: Call<Articles>?, t: Throwable?) {
                var d = ""
            }

            override fun onResponse(
                call: Call<Articles>?,
                response: Response<Articles>?
            ) {
                articlesList.value = response!!.body().articles
            }


        })
    }
}