package com.movietime.domain.interactors.movie

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.singleOrNull

class GetMovieIdsUseCase @AssistedInject constructor(
    @Assisted private val movieDetailRepository: MovieDetailRepository
) {
    val movieIds = movieDetailRepository.movieIds

    suspend fun fetchMovieIds() {
        movieDetailRepository.movieDetail.singleOrNull()?.imdbId?.let { imdbId ->
            movieDetailRepository.fetchMovieIds(imdbId)
        }
    }
}

@AssistedFactory
interface GetMovieIdsUseCaseFactory {
    fun create(movieDetailRepository: MovieDetailRepository): GetMovieIdsUseCase
}