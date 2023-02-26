package com.volozhinsky.newsviewer.domain

import com.volozhinsky.newsviewer.domain.models.Article

interface NewsRepository {

    suspend fun getNews(keyword: String): List<Article>

    fun setUserCountry(country: String)

}