package com.movietime.movie.detail.presentation.model

import com.movietime.movie.domain.model.MovieDetail

data class UiMovieDetail(
    val backdropPath: String,
    val overview: String,
    val tagline: String,
    val title: String
)

fun MovieDetail.toUiMovieDetail(): UiMovieDetail =
    UiMovieDetail(
        backdropPath,
        overview,
        tagline.orEmpty(),
        title
    )
