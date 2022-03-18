package com.example.movietime.movies.detail

import com.fasterxml.jackson.annotation.JsonProperty

data class VideosResponse(
    @JsonProperty("id") val id: Int,
    @JsonProperty("results") val results: List<Video>,
)