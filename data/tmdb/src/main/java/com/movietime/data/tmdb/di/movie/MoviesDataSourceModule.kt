package com.movietime.data.tmdb.di.movie

import com.movietime.data.tmdb.datasource.TmdbMoviesDataSource
import com.movietime.data.tmdb.di.network.NetworkModule.Companion.TMDB_RETROFIT
import com.movietime.data.tmdb.service.MoviesService
import com.movietime.domain.repository.movie.MoviesDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named


@InstallIn(SingletonComponent::class)
@Module
abstract class MoviesDataSourceModule {

    @Binds
    @Reusable
    abstract fun bindMoviesRemoteDataSource(tmdbMoviesDataSource: TmdbMoviesDataSource): MoviesDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    object MoviesServiceModule {

        @Provides
        @Reusable
        fun provideMoviesService(@Named(TMDB_RETROFIT) retrofit: Retrofit): MoviesService {
            return retrofit.create(MoviesService::class.java)
        }
    }
}