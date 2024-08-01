package com.movietime.domain.interactors.tvshow

import com.movietime.domain.model.TvShowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTvShowRecommendationsUseCase @Inject constructor(
    private val tvShowDetailRepository: TvShowDetailRepository
) {
    private var recommendationsPage = 1
    private val _recommendedTvShows = MutableStateFlow<List<TvShowPreview>>(emptyList())
    val recommendedTvShows : StateFlow<List<TvShowPreview>> = _recommendedTvShows

    /**
     * Get the first page of recommendations given a movie id.
     */
    suspend fun refresh(tmdbTvShowId: Int) {
        recommendationsPage = 1
        _recommendedTvShows.value = tvShowDetailRepository.getRecommendations(tmdbTvShowId, recommendationsPage).first()
    }

    /**
     * Get the following page of recommendations given a movie id.
     */
    suspend fun fetchMore(tmdbTvShowId: Int) {
        recommendationsPage++
        _recommendedTvShows.value = _recommendedTvShows.value.plus(tvShowDetailRepository.getRecommendations(tmdbTvShowId, recommendationsPage).first())
    }
}