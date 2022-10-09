package com.movietime.movie.detail.presentation.model

import com.movietime.movie.domain.model.Video

data class UiVideo(
    val key: String
)

fun Video.toUiVideo() = UiVideo(key)
