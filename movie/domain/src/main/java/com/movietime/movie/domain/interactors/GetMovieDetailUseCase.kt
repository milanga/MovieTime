package com.movietime.movie.domain.interactors

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetMovieDetailUseCase @AssistedInject constructor(
    @Assisted private val movieDetailRepository: MovieDetailRepository
) {
    val movieDetail = movieDetailRepository.movieDetail

    suspend fun fetchMovieDetail() = movieDetailRepository.fetchMovieDetail()
}

@AssistedFactory
interface GetMovieDetailUseCaseFactory {
    fun create(movieDetailRepository: MovieDetailRepository): GetMovieDetailUseCase
}