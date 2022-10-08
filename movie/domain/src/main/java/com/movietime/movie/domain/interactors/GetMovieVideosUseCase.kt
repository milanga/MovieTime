package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val movieDetailRepositoryImpl: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<List<Video>> = movieDetailRepositoryImpl.getMovieVideos(movieId)
}