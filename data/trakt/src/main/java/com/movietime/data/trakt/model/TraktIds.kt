package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TraktIds(
    @JsonProperty("trakt") val trakt: Int,
    @JsonProperty("slug") val slug: String,
    @JsonProperty("imdb") val imdb: String,
    @JsonProperty("tmdb") val tmdb: Int,
    @JsonProperty("tvdb") val tvdb: Int?
)