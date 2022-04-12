package com.movietime.movietime.moviedetail.interactors

import com.movietime.movietime.moviedetail.data.MovieDetailRepository
import com.movietime.movietime.moviedetail.domain.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<MovieDetail> = movieDetailRepository.getMovieDetail(movieId)
}