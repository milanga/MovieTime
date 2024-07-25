package com.movietime.domain.interactors

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetMovieVideosUseCase @AssistedInject constructor(
    @Assisted private val movieDetailRepository: MovieDetailRepository
) {
    val movieVideos = movieDetailRepository.movieVideos

    suspend fun fetchMovieVideos() = movieDetailRepository.fetchMovieVideos()
}

@AssistedFactory
interface GetMovieVideosUseCaseFactory {
    fun create(movieDetailRepository: MovieDetailRepository): GetMovieVideosUseCase
}