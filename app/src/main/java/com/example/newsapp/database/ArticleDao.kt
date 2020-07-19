package com.example.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.models.Article

@Dao
interface ArticleDao {
    @Query("Select * from favouriteNews")
    fun getAllFavouriteNews(): List<Article>

    @Insert
    fun addFavouriteNews(article: Article)

    @Delete
    fun deleteFavouriteNews(article: Article)
}