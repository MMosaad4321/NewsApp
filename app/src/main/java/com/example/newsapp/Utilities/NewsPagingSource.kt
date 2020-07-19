package com.example.newsapp.Utilities

import androidx.paging.PageKeyedDataSource
import com.example.newsapp.models.Article
import com.example.newsapp.network.NewsRepository

class NewsPagingSource : PageKeyedDataSource<Int, List<Article>>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, List<Article>>
    ) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, List<Article>>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, List<Article>>) {

    }
}