package com.movietime.movie.domain.interactors

import javax.inject.Inject

class TopRatedMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    val topRatedMovies = moviesRepository.topRatedMovies

    suspend fun loadPage(page: Int = 1) {
        moviesRepository.fetchTopRatedMovies(page)
    }
}