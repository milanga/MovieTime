package com.movietime.data.trakt.interceptor

import com.movietime.data.trakt.di.auth.AuthModule.Companion.TRAKT_CLIENT_ID_NAME
import com.movietime.domain.interactors.auth.GetToken
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class TraktInterceptor @Inject constructor(
    @Named(TRAKT_CLIENT_ID_NAME)
    private val traktClientID: String,
    private val getToken: GetToken
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHeaders = original.headers

        val newHeadersBuilder = originalHeaders.newBuilder()
            .add(
                API_KEY_NAME,
                traktClientID
            )
            .add(API_VERSION_NAME, "2")

        runBlocking {
            val token = getToken()
            if (token != null) {
                newHeadersBuilder.add(AUTHORIZATION_NAME, "Bearer ${token.accessToken}")
            }
        }

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .headers(newHeadersBuilder.build())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY_NAME = "trakt-api-key"
        private const val API_VERSION_NAME = "trakt-api-version"
        private const val AUTHORIZATION_NAME = "Authorization"
    }
}