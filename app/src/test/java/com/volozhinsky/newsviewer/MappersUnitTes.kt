package com.volozhinsky.newsviewer

import com.volozhinsky.data.data.database.ArticleEntity
import com.volozhinsky.data.data.mappers.ArticleEntityMapper
import com.volozhinsky.data.data.models.ArticleResponse
import com.volozhinsky.domain.models.Article
import com.volozhinsky.newsviewer.ui.mappers.ArticleUIMapper
import com.volozhinsky.newsviewer.ui.models.ArticleUI
import org.junit.Assert
import org.junit.Test

class MappersUnitTes {

    @Test
    fun `testing mapping Article to ArticleUI`() {
        val expectedArticleUI = ArticleUI("Title", "Http://test/com/test.jpg", "Http://test/com/")
        val testingArticle = Article("Title", "Http://test/com/test.jpg", "Http://test/com/")
        val mapper = ArticleUIMapper()
        Assert.assertEquals(expectedArticleUI, mapper(testingArticle))
    }

    @Test
    fun `testing mapping ArticleResponse to ArticleEntity`() {
        val expectedArticleEntity =
            ArticleEntity("Http://test/com/", "Title", "Http://test/com/test.jpg")
        val testingArticleResponse =
            ArticleResponse("Title", "Http://test/com/test.jpg", "Http://test/com/")
        val mapper = ArticleEntityMapper()
        Assert.assertEquals(expectedArticleEntity, mapper(testingArticleResponse))
    }

    @Test
    fun `testing mapping ArticleResponse to ArticleEntity with null`() {
        val expectedArticleEntity = ArticleEntity("", null, null)
        val testingArticleResponse = ArticleResponse(null, null, null)
        val mapper = ArticleEntityMapper()
        Assert.assertEquals(expectedArticleEntity, mapper(testingArticleResponse))
    }

    @Test
    fun `testing mapping ArticleEntity to Article`() {
        val expectedArticle = Article("Title", "Http://test/com/test.jpg", "Http://test/com/")
        val testingArticleEntity =
            ArticleEntity("Http://test/com/", "Title", "Http://test/com/test.jpg")
        val mapper = ArticleEntityMapper()
        Assert.assertEquals(expectedArticle, mapper.mapToArticle(testingArticleEntity))
    }

    @Test
    fun `testing mapping ArticleEntity to Article with null`() {
        val expectedArticle = Article("", "", "")
        val testingArticleEntity = ArticleEntity("", null, null)
        val mapper = ArticleEntityMapper()
        Assert.assertEquals(expectedArticle, mapper.mapToArticle(testingArticleEntity))
    }
}