package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TraktMediaDetail(
    @JsonProperty("title") val title: String,
    @JsonProperty("year") val year: Int,
    @JsonProperty("ids") val ids: TraktIds
)