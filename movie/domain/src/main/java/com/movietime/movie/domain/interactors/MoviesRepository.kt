package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MoviePreview
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(page: Int): Flow<List<MoviePreview>>
    fun getTopRatedMovies(page: Int): Flow<List<MoviePreview>>
    fun getUpcomingMovies(page: Int): Flow<List<MoviePreview>>
}