package com.milanga.core.networking

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor(private val tmdbApiKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(
                API_KEY_NAME,
                tmdbApiKey
            )
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY_NAME = "api_key"
    }
}