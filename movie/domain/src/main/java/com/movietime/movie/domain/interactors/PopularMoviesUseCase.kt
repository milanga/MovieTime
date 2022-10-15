package com.movietime.movie.domain.interactors

import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    val popularMovies = moviesRepository.popularMovies

    suspend fun loadPage(page: Int = 1) {
        moviesRepository.fetchPopularMovies(page)
    }
}