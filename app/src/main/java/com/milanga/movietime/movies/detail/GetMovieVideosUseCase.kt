package com.milanga.movietime.movies.detail

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<List<Video>> = movieDetailRepository.getMovieVideos(movieId)
}