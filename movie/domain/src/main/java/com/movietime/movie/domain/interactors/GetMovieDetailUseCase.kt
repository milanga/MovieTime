package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    val movieDetail = movieDetailRepository.movieDetail

    suspend fun fetchMovieDetail(movieId: Int) = movieDetailRepository.fetchMovieDetail(movieId)
}