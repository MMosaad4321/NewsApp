package com.example.newsapp.ui.Main.ViewModel

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Common.ApplicationDelegate
import com.example.newsapp.models.Article
import com.example.newsapp.network.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel: ViewModel() {
    var articlesList = MutableLiveData<List<Article>> ()

    val articleObserver : List<Article>
    get() = articlesList.value ?: arrayListOf()

    val navigateToDetails = MutableLiveData<String>()
    val shareNews = MutableLiveData<String>()
    val favoriteNews = MutableLiveData<Article>()

    fun getDataFromNetwork(){
        viewModelScope.launch {
           NewsRepository.getNews {
               articlesList.value = it?.articles
           }
        }

    }
    fun getDataFromRoom(){
        viewModelScope.launch {
            NewsRepository.getFavouriteNews {
                articlesList.value = it
            }
        }
    }


    fun getArticle(index : Int) : Article = articleObserver[index]



    fun getFormattedDate(publishedAt : String? ) : String {

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = format.parse(publishedAt ?: "")
        return DateFormat.format("dd,MMM yyyy", date) as String
    }

    fun addNews(article: Article) {
        viewModelScope.launch {
            NewsRepository.addNewsRepo(article)
        }
    }

    fun deleteNews(article: Article){
        viewModelScope.launch {
            NewsRepository.deleteNewsRepo(article)
        }
    }

}