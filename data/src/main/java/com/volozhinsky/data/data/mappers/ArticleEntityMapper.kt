package com.volozhinsky.data.data.mappers

import com.volozhinsky.data.data.database.ArticleEntity
import com.volozhinsky.data.data.models.ArticleResponse
import com.volozhinsky.domain.models.Article
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
        com.volozhinsky.domain.models.Article(
            title = title ?: "",
            urlToImage = urlToImage ?: "",
            url = url
        )
    }
}


