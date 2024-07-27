package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbNetwork(
    @JsonProperty("id") val id: Int,
    @JsonProperty("logo_path") val logoPath: String?,
    @JsonProperty("name") val name: String,
    @JsonProperty("origin_country") val originCountry: String
)