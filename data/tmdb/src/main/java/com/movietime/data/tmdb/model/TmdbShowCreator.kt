package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbShowCreator(
    @JsonProperty("id") val id: Int,
    @JsonProperty("credit_id") val creditId: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("original_name") val originalName: String,
    @JsonProperty("gender") val gender: Int,
    @JsonProperty("profile_path") val profilePath: String
)

