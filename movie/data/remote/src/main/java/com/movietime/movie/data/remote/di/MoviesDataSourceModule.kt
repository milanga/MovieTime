package com.movietime.movie.data.remote.di

import com.movietime.movie.data.remote.MoviesRemoteDataSource
import com.movietime.movie.data.remote.MoviesService
import com.movietime.movie.data.remote.mappers.MoviePreviewMapper
import com.movietime.movie.model.repository.MoviesDataSource
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
        fun provideMoviePreviewMapper(
            @BackdropBaseUrl backdropBaseUrl: String,
            @PosterBaseUrl posterBaseUrl: String
        ): MoviePreviewMapper = MoviePreviewMapper(posterBaseUrl, backdropBaseUrl)

        @Provides
        @Reusable
        @BackdropBaseUrl
        fun provideBackdropImageBaseUrl(): String = "http://image.tmdb.org/t/p/original"

        @Provides
        @Reusable
        @PosterBaseUrl
        fun providePosterImageBaseUrl(): String = "http://image.tmdb.org/t/p/w500"
    }
}