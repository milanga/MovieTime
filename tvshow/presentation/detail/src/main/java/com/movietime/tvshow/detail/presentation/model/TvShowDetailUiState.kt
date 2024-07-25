package com.movietime.tvshow.detail.presentation.model

import com.movietime.core.views.poster.model.PosterItem

sealed interface TvShowDetailUiState {
    object Error : TvShowDetailUiState
    object Loading : TvShowDetailUiState
    data class Content(
        val tvShowDetail: UiTvShowDetail,
        val tvShowVideos: List<UiVideo>,
        val tvShowRecommendations: List<PosterItem>
    ) : TvShowDetailUiState
}