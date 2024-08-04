package com.movietime.data.trakt.di.network

import com.movietime.data.trakt.interceptor.TraktInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://api.trakt.tv"
        private const val TRAKT_OK_HTTP_CLIENT = "TRAKT_OK_HTTP_CLIENT"
        const val TRAKT_RETROFIT = "TRAKT_RETROFIT"
        private const val TRAKT_OK_HTTP_CLIENT_AUTH = "TRAKT_OK_HTTP_CLIENT_AUTH"
        const val TRAKT_RETROFIT_AUTH = "TRAKT_RETROFIT_AUTH"
    }

    @Singleton
    @Provides
    @Named(TRAKT_RETROFIT)
    fun provideRetrofit(
        @Named(TRAKT_OK_HTTP_CLIENT)
        httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named(TRAKT_RETROFIT_AUTH)
    fun provideAuthRetrofit(
        @Named(TRAKT_OK_HTTP_CLIENT_AUTH)
        httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Named(TRAKT_OK_HTTP_CLIENT_AUTH)
    fun provideAuthHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Named(TRAKT_OK_HTTP_CLIENT)
    fun provideHttpClient(
        traktInterceptor: TraktInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(traktInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}