package com.movietime.movie.domain.interactors

import javax.inject.Inject

class GetMovieRecommendationsUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    val recommendedMovies = movieDetailRepository.recommendedMovies

    suspend fun refresh(movieId: Int) = movieDetailRepository.refreshRecommendations(movieId)
    suspend fun fetchMore(movieId: Int) = movieDetailRepository.fetchMoreRecommendations(movieId)
}