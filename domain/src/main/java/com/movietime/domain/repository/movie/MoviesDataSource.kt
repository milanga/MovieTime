package com.movietime.domain.repository.movie

import com.movietime.domain.model.MoviePreview

interface MoviesDataSource {
    suspend fun getPopularMovies(page: Int): List<MoviePreview>
    suspend fun getTopRatedMovies(page: Int): List<MoviePreview>
    suspend fun getUpcomingMovies(page: Int): List<MoviePreview>
    suspend fun getTrendingMovies(page: Int): List<MoviePreview>
}