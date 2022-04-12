package com.milanga.movietime.moviedetail.source.remote.model

import com.fasterxml.jackson.annotation.JsonProperty


data class RemoteGenre(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String
)