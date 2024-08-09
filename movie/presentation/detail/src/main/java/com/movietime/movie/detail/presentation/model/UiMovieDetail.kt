package com.movietime.movie.detail.presentation.model

import com.movietime.domain.model.MovieDetail

data class UiMovieDetail(
    val backdropUrl: String = "",
    val posterUrl: String = "",
    val overview: String = "",
    val tagline: String = "",
    val title: String = "",
    val rating: String = "",
    val releaseDate: String = "",
    val duration: String? = "",
    val genres: List<String> = listOf(),
)

fun MovieDetail.toUiMovieDetail(): UiMovieDetail =
    UiMovieDetail(
        backdropUrl = backdropUrl,
        posterUrl = posterUrl,
        overview = overview,
        tagline = tagline.orEmpty(),
        title = title,
        rating = "%.1f".format(voteAverage),
        releaseDate = releaseDate,
        duration = runtime?.toString(),
        genres = genres.map { it.name }
    )
