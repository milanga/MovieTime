package com.movietime.data.tmdb.di.movie

import com.movietime.data.tmdb.datasource.TmdbMovieDetailDataSource
import com.movietime.data.tmdb.service.MovieDetailService
import com.movietime.domain.repository.movie.MovieDetailDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@InstallIn(SingletonComponent::class)
@Module
abstract class MovieDetailDataSourceModule {

    @Binds
    @Reusable
    abstract fun bindMovieDetailRemoteDataSource(tmdbMovieDetailDataSource: TmdbMovieDetailDataSource): MovieDetailDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    object MovieDetailServiceModule {

        @Provides
        @Reusable
        fun provideMovieDetailService(retrofit: Retrofit): MovieDetailService {
            return retrofit.create(MovieDetailService::class.java)
        }
    }
}