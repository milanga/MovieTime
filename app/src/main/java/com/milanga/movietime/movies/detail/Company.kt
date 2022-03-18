package com.milanga.movietime.movies.detail

import com.fasterxml.jackson.annotation.JsonProperty

data class Company(
    @JsonProperty("name") val name: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("logo_path") private val logoPath: String?,
    @JsonProperty("origin_country") val originCountry: String
)