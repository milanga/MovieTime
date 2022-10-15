package com.movietime.movie.domain.interactors

import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    val popularMovies = moviesRepository.popularMovies

    suspend fun refresh() = moviesRepository.refreshPopularMovies()
    suspend fun fetchMore() = moviesRepository.fetchMorePopularMovies()
}