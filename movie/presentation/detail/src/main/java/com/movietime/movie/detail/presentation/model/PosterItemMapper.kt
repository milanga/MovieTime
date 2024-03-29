package com.movietime.movie.detail.presentation.model

import com.movietime.core.views.model.PosterItem
import com.movietime.movie.domain.model.MoviePreview

fun MoviePreview.toPosterItem() =  PosterItem(id, posterPath, "%.1f".format(rating))