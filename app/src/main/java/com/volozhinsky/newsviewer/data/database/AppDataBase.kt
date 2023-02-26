package com.volozhinsky.newsviewer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class, KeywordsEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun getFilmsDao(): NewsDao
}