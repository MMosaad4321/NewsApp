package com.example.newsapp.ui.Main.HeadLines.ViewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.models.Article
import com.example.newsapp.Common.NewsRepository
import kotlinx.coroutines.launch

class HeadLinesViewModel : BaseViewModel() {
    var mAddFavorite = MutableLiveData<Article>()
    var articlesList = MutableLiveData<List<Article>>()
    var firstTime = 0






    fun getDataFromNetwork() {
        viewModelScope.launch {
            NewsRepository.getNews {
                mLoadingObserver.value = true
                firstTime = 0
                articlesList.value = it?.articles ?: arrayListOf()

            }
        }
    }

    fun searchNewsNetwork(text: String) {
        viewModelScope.launch {

            mLoadingObserver.value = true
            NewsRepository.searchNewsBySource(text) {
                articlesList.value = it?.articles ?: arrayListOf()

            }
        }
    }
    fun getArticle(index : Int ) : Article?  = articlesList.value?.get(index)



     fun addNews(article: Article) {
        viewModelScope.launch {
            NewsRepository.addNewsRepo(article)
        }
    }
    fun isFavourite(article: Article):Boolean{
        val roomArticle = ApplicationDelegate.mDb.articleDao.getSpecificArticle(article.url)
        return article == roomArticle
    }
}