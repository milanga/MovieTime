package com.movietime.movie.detail.presentation.model

import com.movietime.domain.model.MovieDetail

data class UiMovieDetail(
    val backdropUrl: String = "",
    val posterUrl: String = "",
    val overview: String = "Overview",
    val tagline: String = "Tagline",
    val title: String = "Title",
    val rating: String = "8.9",
    val releaseDate: String = "2021-01-01",
    val duration: String? = "115",
    val genres: List<String> = listOf("Action", "Adventure"),
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
