package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbCompany(
    @JsonProperty("name") val name: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("logo_path") val logoPath: String?,
    @JsonProperty("origin_country") val originCountry: String
)