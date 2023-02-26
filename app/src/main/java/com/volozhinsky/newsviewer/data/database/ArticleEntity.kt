package com.volozhinsky.newsviewer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class ArticleEntity(

    @PrimaryKey @ColumnInfo("url") val url: String = "",
    @ColumnInfo("title") val title: String? = null,
    @ColumnInfo("urlToImage") val urlToImage: String? = null
)
