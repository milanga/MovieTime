package com.movietime.movie.domain.repository

import com.movietime.movie.domain.interactors.MoviesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteMoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesDataSource,
): MoviesRepository {
    override fun getPopularMovies(page: Int) = flow{
        emit(remoteDataSource.getPopularMovies(page))
    }
    override fun getTopRatedMovies(page: Int) = flow {
        emit(remoteDataSource.getTopRatedMovies(page))
    }
    override fun getUpcomingMovies(page: Int) = flow {
        emit(remoteDataSource.getUpcomingMovies(page))
    }
}