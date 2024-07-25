package com.movietime.core.views.highlight.model

data class HighlightedItem(
    val id: Int,
    val backdropPath: String,
    val posterPath: String,
    val title: String,
    val overview: String,
    val rating: String
)