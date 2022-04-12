package com.movietime.movietime.movies.data

import com.movietime.movietime.movies.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesDataSource){
    fun getPopularMovies(page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getPopularMovies(page)
    fun getTopRatedMovies(page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getTopRatedMovies(page)
    fun getUpcomingMovies(page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getUpcomingMovies(page)
}