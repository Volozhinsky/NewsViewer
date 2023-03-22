package com.volozhinsky.domain

import com.volozhinsky.domain.models.Article

interface NewsRepository {

    suspend fun getNews(keyword: String): List<Article>

    fun setUserCountry(country: String)

}