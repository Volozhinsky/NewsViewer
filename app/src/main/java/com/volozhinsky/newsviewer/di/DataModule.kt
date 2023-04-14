package com.volozhinsky.newsviewer.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.volozhinsky.newsviewer.data.network.ApiKeyInterceptor
import com.volozhinsky.newsviewer.data.network.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideNewsService(client: OkHttpClient): NewsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    fun okhttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor()).build()
    }

    @Provides
    fun providePrefs(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(PREFS_KEY,MODE_PRIVATE)
    }

    private const val PREFS_KEY = "prefs_key"
}