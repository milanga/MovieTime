package com.movietime.domain.interactors.movie

import com.movietime.domain.model.MoviePreview
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMovieRecommendationsUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    private var recommendationsPage = 1
    private val _recommendedMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    val recommendedMovies : StateFlow<List<MoviePreview>> = _recommendedMovies

    /**
     * Get the first page of recommendations given a movie id.
     */
    suspend fun refresh(tmdbMovieId: Int) {
        recommendationsPage = 1
        _recommendedMovies.value = movieDetailRepository.getRecommendations(tmdbMovieId, recommendationsPage).first()
    }

    /**
     * Get the following page of recommendations given a movie id.
     */
    suspend fun fetchMore(tmdbMovieId: Int) {
        recommendationsPage++
        _recommendedMovies.value = _recommendedMovies.value.plus(movieDetailRepository.getRecommendations(tmdbMovieId, recommendationsPage).first())
    }
}