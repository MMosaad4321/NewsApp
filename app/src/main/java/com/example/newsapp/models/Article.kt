package com.example.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteNews")
data class Article (

    val source: Source? = null,
    val title: String? = null,
    val description: String? = null,
    @PrimaryKey(autoGenerate = false) val url: String,
    val urlToImage: String? = null,
    val publishedAt: String? = null
)