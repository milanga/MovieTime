package com.example.movietime.api

import com.example.movietime.BuildConfig
import com.movietime.app.client.interceptor.ApiKeyInterceptor
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
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val TMDB_API_KEY_NAME = "tmdb_api_key"
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    fun provideHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideLoginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideApiKeyInterceptor(@Named(TMDB_API_KEY_NAME) tmdbApiKey: String): ApiKeyInterceptor {
        return ApiKeyInterceptor(tmdbApiKey)
    }

    @Provides
    @Named(TMDB_API_KEY_NAME)
    fun provideTmdbApiKey(): String = BuildConfig.TMDB_API_KEY

}