package com.example.newsapp.ui.Main.Favourite.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Common.BaseViewModel
import com.example.newsapp.models.Article
import com.example.newsapp.Common.NewsRepository
import kotlinx.coroutines.launch

class HeadLinesLocalViewModel : BaseViewModel() {

    var mLocalArticlesList = MutableLiveData<List<Article>?>()

    var mPlaceHolderObserver  = MutableLiveData<Boolean>()

    fun getDataFromRoom() {
        viewModelScope.launch {
            NewsRepository.getFavouriteNews {
                mLoadingObserver.value = true
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