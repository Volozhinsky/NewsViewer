package com.volozhinsky.newsviewer.di

import com.volozhinsky.newsviewer.data.network.ApiKeyInterceptor
import com.volozhinsky.newsviewer.data.network.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideNewsService(): NewsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(okhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApiService::class.java)
    }

    private fun okhttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor()).build()
    }
}