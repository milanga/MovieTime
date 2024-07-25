package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty


data class TmdbGenre(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String
)