package com.movietime.tvshow.home.presentation

import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.TvShowPreview

fun TvShowPreview.toPosterItem() =  PosterItem(
    id,
    posterUrl,
    "%.1f".format(voteAverage)
)

fun TvShowPreview.toHighlightedItem() = HighlightedItem(
    id,
    backdropUrl,
    posterUrl,
    name,
    overview,
    "%.1f".format(voteAverage)
)