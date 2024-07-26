package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties("media_type")
data class TmdbMoviePreview(
    @JsonProperty("title") val title: String,
    @JsonProperty("original_title") val originalTitle: String,
    @JsonProperty("original_language") val originalLanguage: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("backdrop_path") val backdropPath: String?,
    @JsonProperty("vote_average") val rating: Float,
    @JsonProperty("vote_count") val voteCount: Int,
    @JsonProperty("popularity") val popularity: Float,
    @JsonProperty("overview") val overview: String,
    @JsonProperty("genre_ids") val genreIds: List<Int>,
    @JsonProperty("release_date") val releaseDate: Date?,
    @JsonProperty("adult") val adult: Boolean,
    @JsonProperty("video") val video: Boolean,
)