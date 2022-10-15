package com.movietime.movie.domain.di

import com.movietime.movie.domain.interactors.MovieDetailRepositoryFactory
import com.movietime.movie.domain.interactors.MoviesRepository
import com.movietime.movie.domain.repository.RemoteMovieDetailRepositoryFactory
import com.movietime.movie.domain.repository.RemoteMoviesRepository
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
    abstract fun bindMovieDetailRepositoryFactory(remoteMovieDetailRepositoryFactory: RemoteMovieDetailRepositoryFactory): MovieDetailRepositoryFactory

    @Binds
    @Reusable
    abstract fun bindMovieRepository(remoteMoviesRepository: RemoteMoviesRepository): MoviesRepository
}