package com.volozhinsky.data.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keywords", primaryKeys = ["url", "keyword"])
data class KeywordsEntity(
    @ColumnInfo("url") val url: String = "",
    @ColumnInfo("keyword") val keyWord: String  = "",
)