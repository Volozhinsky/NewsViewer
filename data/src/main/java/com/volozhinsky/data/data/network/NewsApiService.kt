package com.volozhinsky.data.data.network

import android.database.Observable
import com.volozhinsky.data.data.models.Response
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("everything")
    fun getNewsList(@Query("q") keyword: String): Call<Single<Response>>

    @GET("top-headlines")
    fun getAllNewsList(@Query("country") country: String = "uk"): Call<Single<Response>>
}