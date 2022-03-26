package com.milanga.movietime.movies.detail

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<MovieDetail> = movieDetailRepository.getMovieDetail(movieId)
}