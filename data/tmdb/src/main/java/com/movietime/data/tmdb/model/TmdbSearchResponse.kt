package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(value = ["dates"] )
data class TmdbSearchResponse(
    @JsonProperty("page") val page: Int,
    @JsonProperty("results") val results: List<TmdbGenericPreview>,
    @JsonProperty("total_pages") val totalPages: Int?,
    @JsonProperty("total_results") val totalResults: Int?
)