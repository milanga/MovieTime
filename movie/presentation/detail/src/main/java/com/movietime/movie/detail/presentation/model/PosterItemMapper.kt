package com.movietime.movie.detail.presentation.model

import com.movietime.core.views.model.PosterItem
import com.movietime.domain.model.MoviePreview

fun MoviePreview.toPosterItem() =  PosterItem(id, posterUrl, "%.1f".format(rating))