package com.volozhinsky.data.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllIntoNews (vararg article: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllIntoKeywords (vararg article: KeywordsEntity)

   @Query("SELECT articles.url, " +
           "articles.title, " +
           "articles.urlToImage " +
           "FROM articles "+
           "INNER JOIN keywords ON articles.url = keywords.url "+
           "WHERE keywords.keyword = :keyword"
   )
   fun getNews(keyword: String): Flow<List<ArticleEntity>>
}