package com.movietime.movie.detail.presentation.model

import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.core.views.poster.model.MediaType
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.domain.model.MoviePreview

fun MoviePreview.toPosterItem() =  PosterItem(
    id,
    posterUrl,
    "%.1f".format(rating),
    MediaType.Movie
)

fun MoviePreview.toHighlightedItem() = HighlightedItem(
    id,
    backdropUrl,
    posterUrl,
    title,
    overview,
    "%.1f".format(rating)
)