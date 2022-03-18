package com.example.movietime.movies

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(value = ["dates"] )
data class MoviesResponse(
    @JsonProperty("page") val page: Int,
    @JsonProperty("results") val movies: List<MoviePreview>,
    @JsonProperty("total_pages") val totalPages: Int,
    @JsonProperty("total_results") val totalResults: Int
)