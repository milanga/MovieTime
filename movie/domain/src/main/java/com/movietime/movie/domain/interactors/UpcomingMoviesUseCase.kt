package com.movietime.movie.domain.interactors

import javax.inject.Inject

class UpcomingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    val upcomingMovies = moviesRepository.upcomingMovies

    suspend fun loadPage(page: Int = 1) {
        moviesRepository.fetchUpcomingMovies(page)
    }
}