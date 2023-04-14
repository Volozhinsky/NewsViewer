package com.volozhinsky.newsviewer.di

import com.volozhinsky.newsviewer.data.NewsRepositoryImpl
import com.volozhinsky.newsviewer.domain.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryDataModule {

    @Binds
    abstract fun getNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}