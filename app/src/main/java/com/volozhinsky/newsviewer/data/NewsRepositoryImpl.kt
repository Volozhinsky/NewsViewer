package com.volozhinsky.newsviewer.data

import com.volozhinsky.newsviewer.data.database.KeywordsEntity
import com.volozhinsky.newsviewer.data.database.NewsDao
import com.volozhinsky.newsviewer.data.mappers.ArticleEntityMapper
import com.volozhinsky.newsviewer.data.mappers.ArticleMapper
import com.volozhinsky.newsviewer.data.models.ArticleResponse
import com.volozhinsky.newsviewer.data.network.NewsApiService
import com.volozhinsky.newsviewer.data.pref.UserDataSource
import com.volozhinsky.newsviewer.domain.NewsRepository
import com.volozhinsky.newsviewer.domain.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val articleMapper: ArticleMapper,
    private val articleEntityMapper: ArticleEntityMapper,
    private val newsApiService: NewsApiService,
    private val prefs: UserDataSource,
    private val newsDao: NewsDao
) : NewsRepository {

    override suspend fun getNews(keyword: String): List<Article> {
        return withContext(Dispatchers.IO) {

            val userCountry = prefs.getUserCountry()
            val newsListRequest = if (keyword.isBlank()) {
                newsApiService.getAllNewsList(userCountry)
            } else {
                newsApiService.getNewsList(keyword)
            }
            try {
                val response = newsListRequest
                    .execute()
                    .body()
                response?.articlesResponse?.let {articleResponses->
                    this.launch {
                        val kw = if (keyword.isBlank()) userCountry else keyword
                        updateNewsDataBase(articleResponses,kw)
                    }
                }
                response?.articlesResponse?.map { articleMapper(it) } ?: throw Exception()
            }catch (e: Exception) {
                getNewsFromDataBase(keyword)
            }
        }
    }

    override fun setUserCountry(country: String) {
        prefs.setUserCountry(country)
    }

    private fun updateNewsDataBase(listNews: List<ArticleResponse>,keyword: String) {
        val listNewsEntity = listNews.map { articleEntityMapper(it) }
        newsDao.insertAllIntoNews(*listNewsEntity.toTypedArray())
        val listKeywords = makeListKeywords(listNews,keyword)
        newsDao.insertAllIntoKeywords(*listKeywords.toTypedArray())
    }

    private fun getNewsFromDataBase(keyword: String): List<Article> {
        return newsDao.getNews(keyword).map {articleEntityMapper.mapToArticle(it) }
    }

    private fun makeListKeywords(listNews: List<ArticleResponse>,keyword: String): List<KeywordsEntity> {
        return listNews.map {
            KeywordsEntity(it.url ?: "",keyword)
        }
    }
}