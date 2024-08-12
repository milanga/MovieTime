package com.movietime.core.views

data class SharedKeys(
    val id : Int,
    val origin: String,
    val type: SharedElementType
)

enum class SharedElementType {
    Image,
    Background
}