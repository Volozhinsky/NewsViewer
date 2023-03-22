package com.volozhinsky.newsviewer.di

import com.volozhinsky.data.data.NewsRepositoryImpl
import com.volozhinsky.domain.NewsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class RepositoryDataModule {

     @Binds
     @Singleton
     abstract fun getNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}