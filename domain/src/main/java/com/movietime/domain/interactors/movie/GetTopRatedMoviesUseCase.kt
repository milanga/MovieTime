package com.movietime.domain.interactors.movie

import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    val topRatedMovies = moviesRepository.topRatedMovies

    suspend fun refresh() = moviesRepository.refreshTopRatedMovies()
    suspend fun fetchMore() = moviesRepository.fetchMoreTopRatedMovies()
}