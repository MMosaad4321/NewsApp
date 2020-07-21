package com.example.newsapp.ui.Main.DetailsForSource.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.models.Article
import com.example.newsapp.network.NewsRepository
import kotlinx.coroutines.launch

class SourceDetailsViewModel : BaseViewModel() {
    var mAddFavorite = MutableLiveData<Article>()
    var articlesList = MutableLiveData<List<Article>>()
    var articlesTemp = MutableLiveData<List<Article>>()

    fun getDataFromNetwork(id: String?) {
        viewModelScope.launch {
            NewsRepository.getNewsBySource (id!!) {
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