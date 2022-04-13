package com.movietime.movie.data.source.remote.di

import com.movietime.movie.data.source.remote.MoviesRemoteDataSource
import com.movietime.movie.data.MoviesDataSource
import com.movietime.movie.data.source.remote.MoviesService
import com.movietime.movie.data.source.remote.mappers.MoviePreviewMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@InstallIn(SingletonComponent::class)
@Module
abstract class MoviesRemoteSourceModule {

    @Binds
    @Reusable
    abstract fun bindMoviesRemoteDataSource(moviesRemoteDataSource: MoviesRemoteDataSource): MoviesDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    object MoviesServiceModule {

        @Provides
        @Reusable
        fun provideMoviesService(retrofit: Retrofit): MoviesService {
            return retrofit.create(MoviesService::class.java)
        }

        @Provides
        @Reusable
        fun provideRemoteSourceMapper(): MoviePreviewMapper {
            return MoviePreviewMapper
        }
    }
}