package com.movietime.domain.di

import com.movietime.domain.interactors.movie.MovieDetailRepository
import com.movietime.domain.interactors.movie.MoviesRepository
import com.movietime.domain.repository.movie.RemoteMovieDetailRepository
import com.movietime.domain.repository.movie.RemoteMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class MovieModule {

    @Binds
    @Reusable
    abstract fun bindMovieDetailRepository(remoteMovieDetailRepository: RemoteMovieDetailRepository): MovieDetailRepository

    @Binds
    @Reusable
    abstract fun bindMovieRepository(remoteMoviesRepository: RemoteMoviesRepository): MoviesRepository
}