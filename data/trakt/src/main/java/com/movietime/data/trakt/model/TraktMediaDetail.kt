package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TraktMediaDetail(
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("year") val year: Int? = null,
    @JsonProperty("ids") val ids: TraktIds
)