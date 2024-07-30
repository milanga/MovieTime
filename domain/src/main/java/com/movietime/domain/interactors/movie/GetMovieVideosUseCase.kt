package com.movietime.domain.interactors.movie

import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(tmdbMovieId: Int) = movieDetailRepository.getMovieVideos(tmdbMovieId)
}
