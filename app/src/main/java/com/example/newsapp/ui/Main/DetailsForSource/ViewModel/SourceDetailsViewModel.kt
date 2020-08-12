package com.example.newsapp.ui.Main.DetailsForSource.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.models.Article
import com.example.newsapp.Common.NewsRepository
import com.example.newsapp.database.ArticleDao
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger

class SourceDetailsViewModel : BaseViewModel() {
    var mAddFavorite = MutableLiveData<Article>()
    var articlesList = MutableLiveData<List<Article>>()
    private val articleDao : ArticleDao by inject()

    fun getDataFromNetwork(id: String?) {
        viewModelScope.launch {
            newsRepo.getNewsBySource (id!!) {
                articlesList.value = it?.articles ?: arrayListOf()
            }
        }
        
    }

    fun getArticle(index : Int ) : Article?  = articlesList.value?.get(index)

    fun addNews(article: Article) {
        viewModelScope.launch {
            newsRepo.addNewsRepo(article)
        }
    }

    fun isFavourite(article: Article):Boolean{
        val roomArticle = articleDao.getSpecificArticle(article.url)
        return article == roomArticle
    }
}