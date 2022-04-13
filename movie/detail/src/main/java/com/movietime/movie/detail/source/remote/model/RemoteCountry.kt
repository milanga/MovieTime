package com.movietime.movie.detail.source.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RemoteCountry(
    @JsonProperty("name") val name: String,
    @JsonProperty("iso_3166_1") val iso: String
)