package com.volozhinsky.domain

import com.volozhinsky.domain.models.Article
import io.reactivex.Completable
import io.reactivex.Observable


interface NewsRepository {

    fun getNews(keyword: String): Completable

    fun setUserCountry(country: String)

    fun getNewsFromDataBase(keyword: String): Observable<List<Article>>
}