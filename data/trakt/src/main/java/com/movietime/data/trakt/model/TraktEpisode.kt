package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TraktEpisode(
    @JsonProperty("season") val season: Int,
    @JsonProperty("number") val number: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("ids") val ids: TraktIds
)