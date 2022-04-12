package com.movietime.movietime.moviedetail.source.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RemoteLanguage(
    @JsonProperty("english_name") val englishName: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("iso_639_1") val iso: String
)