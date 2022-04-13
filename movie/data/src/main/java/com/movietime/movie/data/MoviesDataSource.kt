package com.movietime.movie.data

import com.movietime.movie.domain.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    fun getPopularMovies(page: Int): Flow<List<MoviePreview>>
    fun getTopRatedMovies(page: Int): Flow<List<MoviePreview>>
    fun getUpcomingMovies(page: Int): Flow<List<MoviePreview>>
}