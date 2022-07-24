package com.movietime.movie.domain.repository

import com.movietime.movie.domain.interactors.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesDataSource,
): MoviesRepository {
    override fun getPopularMovies(page: Int) = remoteDataSource.getPopularMovies(page)
    override fun getTopRatedMovies(page: Int) = remoteDataSource.getTopRatedMovies(page)
    override fun getUpcomingMovies(page: Int) = remoteDataSource.getUpcomingMovies(page)
}