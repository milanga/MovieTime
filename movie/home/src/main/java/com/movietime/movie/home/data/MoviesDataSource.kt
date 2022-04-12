package com.movietime.movie.home.data

import com.movietime.movie.home.domain.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    fun getPopularMovies(page: Int): Flow<List<MoviePreview>>
    fun getTopRatedMovies(page: Int): Flow<List<MoviePreview>>
    fun getUpcomingMovies(page: Int): Flow<List<MoviePreview>>
}