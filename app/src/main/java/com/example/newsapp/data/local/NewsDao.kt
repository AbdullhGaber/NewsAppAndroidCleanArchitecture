package com.example.newsapp.data.local

import androidx.room.*
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article : Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM articles")
     fun getAllArticles() : Flow<List<Article>>

    @Query("SELECT * FROM articles WHERE url = :url")
    suspend fun getArticleByUrl(url : String) : Article?

}