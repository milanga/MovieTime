package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbVideosResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("results") val results: List<TmdbVideo>,
)