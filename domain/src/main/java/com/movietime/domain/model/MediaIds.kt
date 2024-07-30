package com.movietime.domain.model

data class MediaIds(
    val trakt: Int,
    val slug: String,
    val imdb: String?,
    val tmdb: Int,
    val tvdb: Int?,
)