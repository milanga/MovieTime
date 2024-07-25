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
    val popularity: Double,
    val posterUrl: String,
    val firstAirDate: String,
    val name: String,
    val voteAverage: Double,
    val voteCount: Int
)