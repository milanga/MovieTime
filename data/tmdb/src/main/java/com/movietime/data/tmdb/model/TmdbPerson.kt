package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbPersonPreview(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("original_name") val originalName: String,
    @JsonProperty("media_type") val mediaType: String,
    @JsonProperty("adult") val adult: Boolean,
    @JsonProperty("popularity") val popularity: Float,
    @JsonProperty("gender") val gender: Int,
    @JsonProperty("known_for_department") val knownForDepartment: String,
    @JsonProperty("profile_path") val profilePath: String?,
    @JsonProperty("known_for") val knownFor: List<TmdbGenericPreview>
)