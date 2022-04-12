package com.movietime.movietime.moviedetail.interactors

import com.movietime.movietime.movies.domain.MoviePreview
import com.movietime.movietime.moviedetail.data.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieRecommendationsUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int, page: Int = 1): Flow<List<MoviePreview>> =
        movieDetailRepository.getMovieRecommendations(movieId, page)
}