package com.volozhinsky.data.data.models

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("title") val title: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("url") val url: String? = null
)