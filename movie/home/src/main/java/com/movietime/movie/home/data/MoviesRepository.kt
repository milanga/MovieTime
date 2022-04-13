package com.movietime.movie.home.data

import com.movietime.movie.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesDataSource){
    fun getPopularMovies(page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getPopularMovies(page)
    fun getTopRatedMovies(page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getTopRatedMovies(page)
    fun getUpcomingMovies(page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getUpcomingMovies(page)
}