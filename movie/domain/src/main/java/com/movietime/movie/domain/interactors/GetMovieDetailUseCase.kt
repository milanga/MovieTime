package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.repository.MovieDetailRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepositoryImpl
) {
    operator fun invoke(movieId: Int): Flow<MovieDetail> = movieDetailRepository.getMovieDetail(movieId)
}