package com.movietime.movie.model.interactors

import com.movietime.movie.model.model.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(page: Int): Flow<List<MoviePreview>>
    fun getTopRatedMovies(page: Int): Flow<List<MoviePreview>>
    fun getUpcomingMovies(page: Int): Flow<List<MoviePreview>>
}