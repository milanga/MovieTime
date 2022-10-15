package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    val popularMovies: Flow<List<MoviePreview>>
    val topRatedMovies: Flow<List<MoviePreview>>
    val upcomingMovies: Flow<List<MoviePreview>>
    suspend fun fetchPopularMovies(page: Int)
    suspend fun fetchTopRatedMovies(page: Int)
    suspend fun fetchUpcomingMovies(page: Int)
}