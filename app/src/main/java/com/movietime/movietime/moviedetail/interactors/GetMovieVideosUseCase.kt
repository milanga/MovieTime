package com.movietime.movietime.moviedetail.interactors

import com.movietime.movietime.moviedetail.data.MovieDetailRepository
import com.movietime.movie.domain.detail.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<List<Video>> = movieDetailRepository.getMovieVideos(movieId)
}