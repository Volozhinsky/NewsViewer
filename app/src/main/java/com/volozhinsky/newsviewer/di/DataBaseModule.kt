package com.volozhinsky.newsviewer.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.volozhinsky.newsviewer.data.database.AppDataBase
import com.volozhinsky.newsviewer.data.database.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    fun getFilmDao(@ApplicationContext context: Context): NewsDao{
        val db =Room.databaseBuilder(context,AppDataBase::class.java,"main_database")
            .build()
        return db.getFilmsDao()
    }
}