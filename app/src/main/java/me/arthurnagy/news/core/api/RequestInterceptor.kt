package me.arthurnagy.news.core.api

import me.arthurnagy.news.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
            .addHeader("X-Api-Key", BuildConfig.AUTH_KEY)
        return chain.proceed(requestBuilder.build())
    }

}