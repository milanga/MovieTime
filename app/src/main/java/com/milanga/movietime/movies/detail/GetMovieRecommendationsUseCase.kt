package com.milanga.movietime.movies.detail

import com.milanga.movietime.movies.MoviePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieRecommendationsUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    operator fun invoke(movieId: Int, page: Int = 1): Flow<List<MoviePreview>> =
        movieDetailRepository.getMovieRecommendations(movieId, page)
            .map { moviesResponse->
                moviesResponse.movies
            }
}