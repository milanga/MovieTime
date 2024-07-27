package com.movietime.domain.model

sealed interface MediaType {
    object Movie : MediaType
    object TvShow : MediaType
    object Person : MediaType
}