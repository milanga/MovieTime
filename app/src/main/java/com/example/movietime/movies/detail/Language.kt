package com.example.movietime.movies.detail

import com.fasterxml.jackson.annotation.JsonProperty

data class Language(
    @JsonProperty("english_name") val englishName: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("iso_639_1") val iso: String
)