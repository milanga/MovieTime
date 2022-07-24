package com.movietime.movie.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty


data class RemoteGenre(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String
)