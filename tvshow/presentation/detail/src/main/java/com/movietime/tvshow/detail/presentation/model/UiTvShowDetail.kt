package com.movietime.tvshow.detail.presentation.model

import com.movietime.domain.model.TvShowDetail

data class UiTvShowDetail(
    val backdropPath: String,
    val overview: String,
    val tagline: String,
    val title: String
)

fun TvShowDetail.toUiTvShowDetail(): UiTvShowDetail =
    UiTvShowDetail(
        backdropUrl,
        overview,
        tagline,
        name
    )
