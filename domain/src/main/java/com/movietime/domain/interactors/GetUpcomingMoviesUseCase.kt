package com.movietime.domain.interactors

import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    val upcomingMovies = moviesRepository.upcomingMovies

    suspend fun refresh() = moviesRepository.refreshUpcomingMovies()
    suspend fun fetchMore() = moviesRepository.fetchMoreUpcomingMovies()
}