package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    val popularMovies: Flow<List<MoviePreview>>
    val topRatedMovies: Flow<List<MoviePreview>>
    val upcomingMovies: Flow<List<MoviePreview>>
    suspend fun refreshPopularMovies()
    suspend fun fetchMorePopularMovies()

    suspend fun refreshTopRatedMovies()
    suspend fun fetchMoreTopRatedMovies()

    suspend fun refreshUpcomingMovies()
    suspend fun fetchMoreUpcomingMovies()
}