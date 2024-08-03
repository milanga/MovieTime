package com.movietime.movie.detail.presentation.model

import com.movietime.core.views.poster.model.PosterItem

sealed interface MovieDetailUiState {
    object Error : MovieDetailUiState
    object Loading : MovieDetailUiState
    data class Content(
        val movieDetail: UiMovieDetail,
        val movieVideos: List<UiVideo>,
        val movieRecommendations: List<PosterItem>,
        val isMovieInWatchlist: Boolean
    ) : MovieDetailUiState
}