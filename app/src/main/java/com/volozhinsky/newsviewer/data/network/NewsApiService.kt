package com.volozhinsky.newsviewer.data.network

import com.volozhinsky.newsviewer.data.models.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    fun getNewsList(@Query("q") keyword: String): Call<Response>

    @GET("top-headlines")
    fun getAllNewsList(@Query("category") category: String): Call<Response>
}