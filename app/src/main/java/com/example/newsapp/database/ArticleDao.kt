package com.example.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM favouriteNews")
    fun getAllFavouriteNews():List<Article>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavouriteNews(article: Article)

    @Delete
    fun deleteFavouriteNews(article: Article)

    @Query("SELECT * FROM favouriteNews WHERE url = :url")
    fun getSpecificArticle(url : String) : Article

}