package com.movietime.movietime.moviedetail.domain

data class Company(
    val name: String,
    val id: Int,
    private val logoPath: String?,
    val originCountry: String
)