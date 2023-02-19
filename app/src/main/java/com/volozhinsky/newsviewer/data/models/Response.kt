package com.volozhinsky.newsviewer.data.models

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("status") val status:String? = null,
    @SerializedName("articles") val articlesResponse: List<ArticleResponse>
)