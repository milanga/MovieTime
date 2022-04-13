package com.movietime.movie.domain.detail

data class Company(
    val name: String,
    val id: Int,
    private val logoPath: String?,
    val originCountry: String
)