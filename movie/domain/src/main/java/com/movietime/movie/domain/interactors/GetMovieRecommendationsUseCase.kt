package com.movietime.movie.domain.interactors

import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.repository.MovieDetailRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieRecommendationsUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepositoryImpl
) {
    operator fun invoke(movieId: Int, page: Int = 1): Flow<List<MoviePreview>> =
        movieDetailRepository.getMovieRecommendations(movieId, page)
}