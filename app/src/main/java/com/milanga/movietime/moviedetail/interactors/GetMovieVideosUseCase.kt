package com.milanga.movietime.moviedetail.interactors

import com.milanga.movietime.moviedetail.data.MovieDetailRepository
import com.milanga.movietime.moviedetail.domain.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<List<Video>> = movieDetailRepository.getMovieVideos(movieId)
}