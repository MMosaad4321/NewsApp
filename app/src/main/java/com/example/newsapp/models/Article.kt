package com.example.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsapp.database.Converters

@Entity(tableName = "favouriteNews")
data class Article (

    val source: Source? = null,
    val title: String? = null,
    val description: String? = null,
    @PrimaryKey(autoGenerate = false) val url: String,
    val urlToImage: String? = null,
    val publishedAt: String? = null
)