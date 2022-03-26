package com.milanga.movietime.movies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val moviesService: MoviesService): MoviesDataSource {
    override fun getPopularMovies(page: Int): Flow<MoviesResponse> =  flow {
            emit(moviesService.popularMovies(page))
        }.flowOn(Dispatchers.IO)

    override fun getTopRatedMovies(page: Int): Flow<MoviesResponse> = flow {
            emit(moviesService.topRatedMovies(page))
        }.flowOn(Dispatchers.IO)

    override fun getUpcomingMovies(page: Int): Flow<MoviesResponse> = flow {
        emit(moviesService.upcomingMovies(page))
    }.flowOn(Dispatchers.IO)
}