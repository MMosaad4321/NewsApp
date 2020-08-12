package com.example.newsapp.Common

import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.Common.NewsRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

open class BaseViewModel : ViewModel(), KoinComponent {
    var mLoadingObserver = MutableLiveData<Boolean>()
    var mRemoveFavourite = MutableLiveData<Article>()
    var mSharingObserver = MutableLiveData<String>()
    var mShowErrorToast = MutableLiveData<Int>()
    var mShowErrorDialog = MutableLiveData<Pair<String,String>>()
    var navigateDetails = MutableLiveData<String>()
    var mSourceObserver = MutableLiveData<String>()
    val newsRepo : NewsRepository by inject()


    fun getFormattedDate(publishedAt: String?): String {

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = format.parse(publishedAt ?: "")
        return DateFormat.format("dd,MMM yyyy", date) as String
    }

    open fun deleteNews(article: Article) {
        viewModelScope.launch {
            newsRepo.deleteNewsRepo(article)
        }
    }



}