package com.volozhinsky.newsviewer.di

import android.content.Context
import androidx.room.Room
import com.volozhinsky.data.data.database.AppDataBase
import com.volozhinsky.data.data.database.NewsDao
import dagger.Module
import dagger.Provides


@Module
object DataBaseModule {

    @Provides
    fun getFilmDao( context: Context): com.volozhinsky.data.data.database.NewsDao {
        val db =Room.databaseBuilder(context,
            com.volozhinsky.data.data.database.AppDataBase::class.java,"main_database")
            .build()
        return db.getFilmsDao()
    }
}