package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbLanguage(
    @JsonProperty("english_name") val englishName: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("iso_639_1") val iso: String
)