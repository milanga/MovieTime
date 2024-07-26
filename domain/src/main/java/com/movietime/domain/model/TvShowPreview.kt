package com.movietime.domain.model

data class TvShowPreview(
    val adult: Boolean,
    val backdropUrl: String,
    val genreIds: List<Int>,
    val id: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Float,
    val posterUrl: String,
    val firstAirDate: String,
    val name: String,
    val voteAverage: Float,
    val voteCount: Int
): MediaPreview