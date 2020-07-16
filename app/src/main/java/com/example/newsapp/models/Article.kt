package com.example.newsapp.models

import com.example.newsapp.models.Source

data class Article (
    val source: Source? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null
)