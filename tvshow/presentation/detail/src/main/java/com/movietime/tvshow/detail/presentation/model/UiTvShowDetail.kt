package com.movietime.tvshow.detail.presentation.model

import com.movietime.domain.model.TvShowDetail

data class UiTvShowDetail(
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

fun TvShowDetail.toUiTvShowDetail(): UiTvShowDetail =
    UiTvShowDetail(
        backdropUrl = backdropUrl,
        posterUrl = posterUrl,
        overview = overview,
        tagline = tagline,
        title = name,
        rating = "%.1f".format(voteAverage),
        releaseDate = firstAirDate,
        duration = lastEpisodeToAir.runtime.toString(),
        genres = genres.map { it.name }
    )
