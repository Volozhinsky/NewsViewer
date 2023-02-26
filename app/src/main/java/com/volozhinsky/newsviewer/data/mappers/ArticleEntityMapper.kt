package com.volozhinsky.newsviewer.data.mappers

import com.volozhinsky.newsviewer.data.database.ArticleEntity
import com.volozhinsky.newsviewer.data.models.ArticleResponse
import com.volozhinsky.newsviewer.domain.models.Article
import javax.inject.Inject

class ArticleEntityMapper @Inject constructor() {

    operator fun invoke(response: ArticleEntity) = with(response) {
        ArticleResponse(
            title = title,
            urlToImage = urlToImage,
            url = url
        )
    }

    operator fun invoke(article: ArticleResponse) = with(article) {
        ArticleEntity(
            title = title,
            urlToImage = urlToImage,
            url = url ?: ""
        )
    }

    fun mapToArticle(entity: ArticleEntity) = with(entity) {
        Article(
            title = title ?: "",
            urlToImage = urlToImage ?: "",
            url = url
        )
    }
}


