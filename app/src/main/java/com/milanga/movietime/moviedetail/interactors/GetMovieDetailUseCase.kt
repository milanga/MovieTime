package com.milanga.movietime.moviedetail.interactors

import com.milanga.movietime.moviedetail.data.MovieDetailRepository
import com.milanga.movietime.moviedetail.domain.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int): Flow<MovieDetail> = movieDetailRepository.getMovieDetail(movieId)
}