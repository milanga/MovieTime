package com.movietime.domain.interactors.movie

import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(tmdbMovieId: Int) = movieDetailRepository.getMovieDetail(tmdbMovieId)
}