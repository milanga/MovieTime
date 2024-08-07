package com.movietime.data.tmdb.di.network

import com.movietime.data.tmdb.BuildConfig
import com.movietime.data.tmdb.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val TMDB_API_KEY_NAME = "tmdb_api_key"
        private const val IO_DISPATCHER = "io_dispatcher"
        private const val TMDB_OK_HTTP_CLIENT = "TMDB_OK_HTTP_CLIENT"
        const val TMDB_RETROFIT = "TMDB_RETROFIT"
    }

    @Singleton
    @Provides
    @Named(TMDB_RETROFIT)
    fun provideRetrofit(
        @Named(TMDB_OK_HTTP_CLIENT)
        httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    @Named(TMDB_OK_HTTP_CLIENT)
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

    @Provides
    @Named(IO_DISPATCHER)
    fun provideIoDispatcher(): CoroutineContext {
        return Dispatchers.IO
    }
}