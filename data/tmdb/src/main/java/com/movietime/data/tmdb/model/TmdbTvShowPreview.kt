package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties("media_type")
data class TmdbTvShowPreview(
    @JsonProperty("adult") val adult: Boolean,
    @JsonProperty("backdrop_path") val backdropPath: String?,
    @JsonProperty("genre_ids") val genreIds: List<Int>,
    @JsonProperty("id") val id: Int,
    @JsonProperty("origin_country") val originCountry: List<String>,
    @JsonProperty("original_language") val originalLanguage: String,
    @JsonProperty("original_name") val originalName: String,
    @JsonProperty("overview") val overview: String,
    @JsonProperty("popularity") val popularity: Float,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("first_air_date") val firstAirDate: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("vote_average") val voteAverage: Float,
    @JsonProperty("vote_count") val voteCount: Int
)