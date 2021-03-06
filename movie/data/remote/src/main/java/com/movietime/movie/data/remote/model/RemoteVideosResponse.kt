package com.movietime.movie.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RemoteVideosResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("results") val results: List<RemoteVideo>,
)