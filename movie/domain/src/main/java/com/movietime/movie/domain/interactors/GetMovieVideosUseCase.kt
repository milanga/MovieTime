package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    val movieVideos = movieDetailRepository.movieVideos

    suspend fun fetchMovieVideos(movieId: Int) = movieDetailRepository.fetchMovieVideos(movieId)
}