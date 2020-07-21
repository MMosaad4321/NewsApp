package com.example.newsapp.Common

import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.network.NewsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

open class BaseViewModel : ViewModel() {
    var mLoadingObserver = MutableLiveData<Boolean>()
    var mRemoveFavourite = MutableLiveData<Article>()
    var mSharingObserver = MutableLiveData<String>()
    var mShowErrorToast = MutableLiveData<Int>()
    var mShowErrorDialog = MutableLiveData<Pair<String,String>>()
    var navigateDetails = MutableLiveData<String>()
    var mSourceObserver = MutableLiveData<String>()


    fun getFormattedDate(publishedAt: String?): String {

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = format.parse(publishedAt ?: "")
        return DateFormat.format("dd,MMM yyyy", date) as String
    }

    fun deleteNews(article: Article) {
        viewModelScope.launch {
            NewsRepository.deleteNewsRepo(article)
        }
    }



}