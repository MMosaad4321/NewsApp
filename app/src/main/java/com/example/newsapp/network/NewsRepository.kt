package com.example.newsapp.network

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.models.Article
import com.example.newsapp.models.Articles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*

object NewsRepository {

    private val retroApi = RetrofitClient.getClient().create(NewsApi::class.java)

    suspend fun getNews(onComplete: (Articles?) -> Unit) = withContext(Dispatchers.IO) {
        val articles = retroApi.getHeadlines(
            "technology",
            "eg",
            "86cf320fbd4e404db937cb915aef1cb3",
            1
        ).await()
        withContext(Dispatchers.Main) {
            onComplete(articles)
        }


    }

    suspend fun getFavouriteNews(onComplete: (List<Article>?) -> Unit) = withContext(Dispatchers.IO) {
        val articles  = ApplicationDelegate.mDb.articleDao.getAllFavouriteNews()
        withContext(Dispatchers.Main) {
            onComplete(articles)
        }


    }

    suspend fun addNewsRepo(article: Article) = withContext(Dispatchers.IO){
        ApplicationDelegate.mDb.articleDao.addFavouriteNews(article)
    }

    suspend fun deleteNewsRepo(article: Article) = withContext(Dispatchers.IO){
        ApplicationDelegate.mDb.articleDao.deleteFavouriteNews(article)
    }
}