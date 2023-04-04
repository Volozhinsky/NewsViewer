package com.volozhinsky.newsviewer.ui.mappers

import com.volozhinsky.domain.models.Article
import com.volozhinsky.newsviewer.ui.models.ArticleUI
import javax.inject.Inject

class ArticleUIMapper @Inject constructor() {

    operator fun invoke(response: Article) = with(response) {
        ArticleUI(
            title = title,
            urlToImage = urlToImage,
            url = url
        )
    }
}