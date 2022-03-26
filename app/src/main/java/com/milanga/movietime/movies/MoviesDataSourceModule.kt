package com.milanga.movietime.movies

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class MoviesDataSourceModule {

    @Binds
    @Reusable
    abstract fun bindMoviesRemoteDataSource(moviesRemoteDataSource: MoviesRemoteDataSource): MoviesDataSource
}