package com.movietime.domain.model

data class MovieIds(
    val trakt: Int,
    val slug: String,
    val imdb: String,
    val tmdb: Int
)