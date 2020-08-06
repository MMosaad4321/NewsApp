package com.example.newsapp.ui.Main.Favourite.ViewModel

import androidx.lifecycle.LiveData
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
                initLocalList(it)
            }
        }
    }



    fun getLocalArticle(index: Int): Article? = mLocalArticlesList.value?.get(index)

    override fun deleteNews(article: Article) {
        viewModelScope.launch {
            NewsRepository.deleteNewsRepo(article)
            NewsRepository.getFavouriteNews {
               initLocalList(it)
            }
        }
    }


    fun isFavourite(article: Article): Boolean {
        return mLocalArticlesList.value?.contains(article)!!
    }

    private fun initLocalList(it: LiveData<List<Article>>) {
        mLoadingObserver.value = true
        mLocalArticlesList.value = it.value
        mPlaceHolderObserver.value = it.value?.isEmpty()!!
    }

}