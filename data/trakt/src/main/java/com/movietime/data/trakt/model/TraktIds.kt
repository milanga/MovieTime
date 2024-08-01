package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties("tvrage")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TraktIds(
    @JsonProperty("trakt") val trakt: Int? = null,
    @JsonProperty("slug") val slug: String? = null,
    @JsonProperty("imdb") val imdb: String? = null,
    @JsonProperty("tmdb") val tmdb: Int? = null,
    @JsonProperty("tvdb") val tvdb: Int? = null
)