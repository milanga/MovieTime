package com.movietime.domain.model

import java.util.Date

data class MoviePreview(
    override val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    override val id: Int,
    override val posterUrl: String,
    val backdropUrl: String,
    override val rating: Float,
    val voteCount: Int,
    val popularity: Float,
    val overview: String,
    val genreIds: List<Int>,
    val releaseDate: Date?,
    val adult: Boolean,
    val video: Boolean,
): MediaPreview {
    override val mediaType: MediaType
        get() = MediaType.Movie
}