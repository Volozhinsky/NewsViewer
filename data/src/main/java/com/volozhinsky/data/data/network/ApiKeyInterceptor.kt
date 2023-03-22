package com.volozhinsky.data.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("x-api-key", "0749607333754dab83c88a67fe2bdd8a")
        return chain.proceed(requestBuilder.build())
    }
}