package com.movietime.movie.domain.model

data class Video(
    val id: String,
    val iso6391: String,
    val iso31661: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val publishedAt: String
)