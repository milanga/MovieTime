package com.milanga.movietime.moviedetail.source.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RemoteVideo(
    @JsonProperty("id") val id: String,
    @JsonProperty("iso_639_1") val iso6391: String,
    @JsonProperty("iso_3166_1") val iso31661: String,
    @JsonProperty("key") val key: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("site") val site: String,
    @JsonProperty("size") val size: Int,
    @JsonProperty("type") val type: String,
    @JsonProperty("official") val official: Boolean,
    @JsonProperty("published_at") val publishedAt: String
)