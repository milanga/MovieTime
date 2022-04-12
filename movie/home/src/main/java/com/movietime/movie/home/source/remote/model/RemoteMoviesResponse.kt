package com.movietime.movie.home.source.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(value = ["dates"] )
data class RemoteMoviesResponse(
    @JsonProperty("page") val page: Int,
    @JsonProperty("results") val movies: List<RemoteMoviePreview>,
    @JsonProperty("total_pages") val totalPages: Int,
    @JsonProperty("total_results") val totalResults: Int
)