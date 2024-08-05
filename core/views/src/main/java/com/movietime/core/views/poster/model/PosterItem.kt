package com.movietime.core.views.poster.model

data class PosterItem(
    val id: Int,
    val posterUrl: String,
    val rating: String,
    val mediaType: MediaType,
    val title: String = ""
)

sealed class MediaType {
    object Movie : MediaType()
    object TvShow : MediaType()
    object Person : MediaType()
}