package com.example.movietime.movies.detail

import com.fasterxml.jackson.annotation.JsonProperty

data class Country(
    @JsonProperty("name") val name: String,
    @JsonProperty("iso_3166_1") val iso: String
)