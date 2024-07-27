package com.movietime.domain.model

interface GenericPreview {
    val id: Int
    val posterUrl: String
    val rating: Float?
    val mediaType: MediaType
}