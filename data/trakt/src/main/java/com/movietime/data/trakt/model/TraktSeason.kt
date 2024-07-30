package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TraktSeason(
    @JsonProperty("number") val number: Int,
    @JsonProperty("ids") val ids: TraktIds
)