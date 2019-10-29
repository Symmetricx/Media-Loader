package com.symmetric.media_loading_demo.data.remote


import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = originalRequest
            .newBuilder()
            .addHeader("Authorization", ApiConstants.API_KEY)
            .addHeader("lang", "en")
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(request)
    }
}