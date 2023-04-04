package com.volozhinsky.data.data.mappers

import com.volozhinsky.data.data.models.ArticleResponse
import com.volozhinsky.domain.models.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() {
    operator fun invoke(response: ArticleResponse) = with(response) {
        Article(
            title = title ?: "",
            urlToImage = urlToImage ?: "",
            url = url ?: ""
        )
    }
}