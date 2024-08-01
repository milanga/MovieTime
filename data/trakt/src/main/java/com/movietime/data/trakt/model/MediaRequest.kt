package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MediaRequest(
    @field:JsonProperty("movies")
    val movieIds: List<TraktMediaDetail>? = null,
    @field:JsonProperty("shows")
    val tvShowIds: List<TraktMediaDetail>? = null,
    @field:JsonProperty("seasons")
    val seasonIds: List<TraktMediaDetail>? = null,
    @field:JsonProperty("episodes")
    val episodeIds: List<TraktMediaDetail>? = null
)