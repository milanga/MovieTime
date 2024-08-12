package com.movietime.tvshow.detail.presentation.model

import com.movietime.domain.model.TvShowDetail

data class UiTvShowDetail(
    val id: Int = 1,
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

fun TvShowDetail.toUiTvShowDetail(): UiTvShowDetail =
    UiTvShowDetail(
        id = id,
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
