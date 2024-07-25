package com.movietime.domain.model

import java.util.*

data class MoviePreview(
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val id: Int,
    val posterPath: String,
    val backdropPath: String,
    val rating: Float,
    val voteCount: String,
    val popularity: String,
    val overview: String,
    val genreIds: List<Int>,
    val releaseDate: Date?,
    val adult: Boolean,
    val video: Boolean,
)