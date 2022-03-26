package com.milanga.movietime.movies

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesDataSource){
    fun getPopularMovies(page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getPopularMovies(page)
    fun getTopRatedMovies(page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getTopRatedMovies(page)
    fun getUpcomingMovies(page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getUpcomingMovies(page)
}