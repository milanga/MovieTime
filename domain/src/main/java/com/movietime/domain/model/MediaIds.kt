package com.movietime.domain.model

data class MediaIds(
    val trakt: Int? = null,
    val slug: String? = null,
    val imdb: String? = null,
    val tmdb: Int,
    val tvdb: Int? = null,
)