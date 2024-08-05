package com.movietime.search.home.presentation

import com.movietime.core.views.poster.model.PosterItem
import com.movietime.domain.model.GenericPreview
import com.movietime.domain.model.MediaType
import com.movietime.core.views.poster.model.MediaType.Movie as ViewMovie
import com.movietime.core.views.poster.model.MediaType.TvShow as ViewTvShow
import com.movietime.core.views.poster.model.MediaType.Person as ViewPerson

fun GenericPreview.toPosterItem() =  PosterItem(
    id,
    posterUrl,
    rating?.let{"%.1f".format(rating)}?:"",
    mediaType.toViewMediaType(),
    title
)

fun MediaType.toViewMediaType() = when (this) {
    MediaType.Movie -> ViewMovie
    MediaType.TvShow -> ViewTvShow
    MediaType.Person -> ViewPerson
}