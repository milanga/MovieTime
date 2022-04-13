package com.movietime.movie.interactors

import com.movietime.movie.data.MovieDetailRepository
import com.movietime.movie.domain.detail.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<MovieDetail> = movieDetailRepository.getMovieDetail(movieId)
}