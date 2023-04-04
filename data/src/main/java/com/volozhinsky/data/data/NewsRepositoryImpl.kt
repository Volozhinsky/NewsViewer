package com.volozhinsky.data.data

import com.volozhinsky.data.data.database.KeywordsEntity
import com.volozhinsky.data.data.database.NewsDao
import com.volozhinsky.data.data.mappers.ArticleEntityMapper
import com.volozhinsky.data.data.mappers.ArticleMapper
import com.volozhinsky.data.data.models.ArticleResponse
import com.volozhinsky.data.data.network.NewsApiService
import com.volozhinsky.data.data.pref.UserDataSource
import com.volozhinsky.domain.NewsRepository
import com.volozhinsky.domain.models.Article
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val articleMapper: ArticleMapper,
    private val articleEntityMapper: ArticleEntityMapper,
    private val newsApiService: NewsApiService,
    private val prefs: UserDataSource,
    private val newsDao: NewsDao
) : NewsRepository {

    override fun getNews(keyword: String): Completable {

        val userCountry = prefs.getUserCountry()
        val newsListRequest = if (keyword.isBlank()) {
            newsApiService.getAllNewsList(userCountry)
        } else {
            newsApiService.getNewsList(keyword)
        }

        val response = newsListRequest
            .execute()
            .body()

        return response?.flatMapCompletable { mappedResponse ->
            mappedResponse?.articlesResponse?.let { articleResponses ->
                val kw = if (keyword.isBlank()) userCountry else keyword
                updateNewsDataBase(articleResponses, kw)
                Completable.complete()
            }
        } ?: throw Exception()
    }

    override fun setUserCountry(country: String) {
        prefs.setUserCountry(country)
    }

    private fun updateNewsDataBase(listNews: List<ArticleResponse>, keyword: String) {
        val listNewsEntity = listNews.map { articleEntityMapper(it) }
        newsDao.insertAllIntoNews(*listNewsEntity.toTypedArray())
        val listKeywords = makeListKeywords(listNews, keyword)
        newsDao.insertAllIntoKeywords(*listKeywords.toTypedArray())
    }

    override fun getNewsFromDataBase(keyword: String): Observable<List<Article>> {
        return newsDao.getNews(keyword).map { listArticle ->
            listArticle.map { articleEntityMapper.mapToArticle(it) }
        }
    }

    private fun makeListKeywords(
        listNews: List<ArticleResponse>,
        keyword: String
    ): List<KeywordsEntity> {
        return listNews.map {
            KeywordsEntity(it.url ?: "", keyword)
        }
    }
}