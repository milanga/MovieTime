package com.movietime.movie.domain.interactors

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetMovieRecommendationsUseCase @AssistedInject constructor(
    @Assisted private val movieDetailRepository: MovieDetailRepository
) {
    val recommendedMovies = movieDetailRepository.recommendedMovies

    suspend fun refresh() = movieDetailRepository.refreshRecommendations()
    suspend fun fetchMore() = movieDetailRepository.fetchMoreRecommendations()
}

@AssistedFactory
interface GetMovieRecommendationsUseCaseFactory {
    fun create(movieDetailRepository: MovieDetailRepository): GetMovieRecommendationsUseCase
}