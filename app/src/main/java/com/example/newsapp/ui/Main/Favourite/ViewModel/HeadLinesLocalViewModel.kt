package com.example.newsapp.ui.Main.Favourite.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.models.Article
import com.example.newsapp.network.NewsRepository
import kotlinx.coroutines.launch

class HeadLinesLocalViewModel : BaseViewModel() {

    var mLocalArticlesList = MutableLiveData<List<Article>?>()
    var mLocalArticlesTemp = MutableLiveData<List<Article>?>()

    var mPlaceHolderObserver  = MutableLiveData<Boolean>()
    var firstLoad = 0

    fun getDataFromRoom() {
        viewModelScope.launch {
            NewsRepository.getFavouriteNews {
                firstLoad = 0
                mLocalArticlesList.value = it.value

                mPlaceHolderObserver.value = it.value?.isEmpty()!!
            }
        }
    }


    fun getLocalArticle(index: Int): Article? = mLocalArticlesList.value?.get(index)

    fun isFavourite(article: Article): Boolean {
        return mLocalArticlesList.value?.contains(article)!!
    }
}