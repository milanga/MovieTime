package com.movietime.tvshow.detail.presentation.model

import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.core.views.poster.model.MediaType
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.domain.model.TvShowPreview

fun TvShowPreview.toPosterItem() =  PosterItem(
    id,
    posterUrl,
    "%.1f".format(rating),
    MediaType.TvShow
)

fun TvShowPreview.toHighlightedItem() = HighlightedItem(
    id,
    backdropUrl,
    posterUrl,
    name,
    overview,
    "%.1f".format(rating)
)