package com.movietime.movie.domain.di

import com.movietime.movie.domain.interactors.MovieDetailRepository
import com.movietime.movie.domain.interactors.MoviesRepository
import com.movietime.movie.domain.repository.MovieDetailRepositoryImpl
import com.movietime.movie.domain.repository.MoviesRepositoryImpl
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
    abstract fun bindMovieDetailRepository(movieDetailRepositoryImpl: MovieDetailRepositoryImpl): MovieDetailRepository

    @Binds
    @Reusable
    abstract fun bindMovieRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository
}