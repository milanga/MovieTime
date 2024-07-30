package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.movietime.domain.model.Episode

data class TraktListEntry(
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("id") val id: Int,
    @JsonProperty("listed_at") val listedAt: String,
    @JsonProperty("notes") val notes: String?,
    @JsonProperty("type") val type: String,
    @JsonProperty("episode") val episode: Episode,
    @JsonProperty("show") val show: TraktMediaDetail,
    @JsonProperty("season") val season: TraktSeason,
    @JsonProperty("movie") val movie: TraktMediaDetail
)