package com.volozhinsky.newsviewer.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.volozhinsky.data.data.network.ApiKeyInterceptor
import com.volozhinsky.data.data.network.NewsApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object DataModule {

    @Provides
    fun provideNewsService(client: OkHttpClient): com.volozhinsky.data.data.network.NewsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(com.volozhinsky.data.data.network.NewsApiService::class.java)
    }

    @Provides
    fun okhttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(com.volozhinsky.data.data.network.ApiKeyInterceptor()).build()
    }

    @Provides
    fun providePrefs( context: Context): SharedPreferences{
        return context.getSharedPreferences(PREFS_KEY,MODE_PRIVATE)
    }

    private const val PREFS_KEY = "prefs_key"
}