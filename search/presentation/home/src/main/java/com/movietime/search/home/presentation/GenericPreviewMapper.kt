package com.movietime.search.home.presentation

import com.movietime.core.views.poster.model.PosterItem
import com.movietime.domain.model.GenericPreview

fun GenericPreview.toPosterItem() =  PosterItem(
    id,
    posterUrl,
    ""
)