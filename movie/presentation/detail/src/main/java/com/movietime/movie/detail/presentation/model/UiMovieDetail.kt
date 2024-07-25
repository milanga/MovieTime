package com.movietime.movie.detail.presentation.model

import com.movietime.domain.model.MovieDetail

data class UiMovieDetail(
    val backdropPath: String,
    val overview: String,
    val tagline: String,
    val title: String
)

fun MovieDetail.toUiMovieDetail(): UiMovieDetail =
    UiMovieDetail(
        backdropUrl,
        overview,
        tagline.orEmpty(),
        title
    )
