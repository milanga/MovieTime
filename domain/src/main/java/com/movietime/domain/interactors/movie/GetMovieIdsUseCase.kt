package com.movietime.domain.interactors.movie

import javax.inject.Inject

class GetMovieIdsUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(imdbMovieId: String) = movieDetailRepository.getMovieIds(imdbMovieId)
}