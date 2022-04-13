package com.movietime.movie.interactors

import com.movietime.movie.data.MovieDetailRepository
import com.movietime.movie.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieRecommendationsUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int, page: Int = 1): Flow<List<MoviePreview>> =
        movieDetailRepository.getMovieRecommendations(movieId, page)
}