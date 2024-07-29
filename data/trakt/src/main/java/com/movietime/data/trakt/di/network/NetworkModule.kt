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