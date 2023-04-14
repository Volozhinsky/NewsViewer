package com.volozhinsky.newsviewer.data

import com.volozhinsky.newsviewer.data.mappers.ArticleMapper
import com.volozhinsky.newsviewer.data.models.Response
import com.volozhinsky.newsviewer.data.network.NewsApiService
import com.volozhinsky.newsviewer.data.pref.UserDataSource
import com.volozhinsky.newsviewer.domain.NewsRepository
import com.volozhinsky.newsviewer.domain.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val articleMapper: ArticleMapper,
    private val newsApiService: NewsApiService,
    private val prefs: UserDataSource
) : NewsRepository {

    override suspend fun getNews(keyword: String): List<Article> {
        return withContext(Dispatchers.IO) {
            val userCountry = prefs.getUserCountry()
            val newsListRequest = if (keyword.isBlank()) {
                newsApiService.getAllNewsList(userCountry)
            } else {
                newsApiService.getNewsList(keyword)
            }
            val response = newsListRequest
                .execute()
                .body()
            response?.articlesResponse?.map { articleMapper(it) } ?: throw Exception()
        }
    }

    override fun setUserCountry(country: String) {
        prefs.setUserCountry(country)
    }
}