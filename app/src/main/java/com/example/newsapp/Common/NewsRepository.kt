package com.example.newsapp.Common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.NewsDB
import com.example.newsapp.models.Article
import com.example.newsapp.models.Articles
import com.example.newsapp.network.NewsApi
import com.example.newsapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.*

class NewsRepository : KoinComponent {

    private val retroApi : NewsApi by inject()
    private val articleDao : ArticleDao by inject()

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

    suspend fun getFavouriteNews(onComplete: (LiveData<List<Article>> ) -> Unit) = withContext(Dispatchers.IO) {
        val articles  = articleDao.getAllFavouriteNews()
        val liveArticles = MutableLiveData<List<Article>>()

        withContext(Dispatchers.Main) {
            liveArticles.value  = articles
            onComplete(liveArticles)
        }


    }

    suspend fun getNewsBySource(id :String,onComplete: (Articles?) -> Unit) = withContext(Dispatchers.IO) {
        val articles = retroApi.getHeadlinesBySource(
            id,
            "86cf320fbd4e404db937cb915aef1cb3"
        ).await()
        withContext(Dispatchers.Main) {
            onComplete(articles)
        }


    }

    suspend fun searchNewsBySource(text :String,onComplete: (Articles?) -> Unit) = withContext(Dispatchers.IO) {
        val articles = retroApi.searchNews(
            text,
            "publishedAt",
            "86cf320fbd4e404db937cb915aef1cb3"
        ).await()
        withContext(Dispatchers.Main) {
            onComplete(articles)
        }


    }

    suspend fun addNewsRepo(article: Article) = withContext(Dispatchers.IO){
        articleDao.addFavouriteNews(article)
    }

    suspend fun deleteNewsRepo(article: Article) = withContext(Dispatchers.IO){
        articleDao.deleteFavouriteNews(article)
    }
}