package com.movietime.domain.interactors.movie

import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    val trendingMovies = moviesRepository.trendingMovies

    suspend fun refresh() = moviesRepository.refreshTrendingMovies()
    suspend fun fetchMore() = moviesRepository.fetchMoreTrendingMovies()
}