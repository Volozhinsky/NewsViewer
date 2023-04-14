package com.volozhinsky.newsviewer.data.mappers

import com.volozhinsky.newsviewer.data.models.ArticleResponse
import com.volozhinsky.newsviewer.domain.models.Article
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