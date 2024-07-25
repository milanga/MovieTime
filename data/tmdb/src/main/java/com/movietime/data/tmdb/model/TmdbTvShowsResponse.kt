package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbTvShowsResponse(
    @JsonProperty("page") val page: Int,
    @JsonProperty("results") val results: List<TmdbTvShowPreview>
)