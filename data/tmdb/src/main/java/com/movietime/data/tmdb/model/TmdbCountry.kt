package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbCountry(
    @JsonProperty("name") val name: String,
    @JsonProperty("iso_3166_1") val iso: String
)