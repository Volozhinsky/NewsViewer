package com.volozhinsky.domain

import com.volozhinsky.domain.models.Article
import kotlinx.coroutines.flow.Flow


interface NewsRepository {

    suspend fun getNews(keyword: String)

    fun setUserCountry(country: String)

    fun getNewsFromDataBase(keyword: String): Flow<List<Article>>
}