package com.movietime.movie.home.presentation.model

import com.movietime.domain.model.MoviePreview

data class HighlightedItem(
    val id: Int,
    val backdropPath: String,
    val posterPath: String,
    val title: String,
    val overview: String,
    val rating: String
)

fun MoviePreview.toHighlightedItem() = HighlightedItem(
    id,
    backdropUrl,
    posterUrl,
    title,
    overview,
    "%.1f".format(rating)
)