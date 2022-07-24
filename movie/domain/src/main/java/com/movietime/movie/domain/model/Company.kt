package com.movietime.movie.domain.model

data class Company(
    val name: String,
    val id: Int,
    val logoPath: String?,
    val originCountry: String
)