package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

const val MEDIA_TYPE_MOVIE = "movie"
const val MEDIA_TYPE_TV = "tv"
const val MEDIA_TYPE_PERSON = "person"

data class TmdbGenericPreview(
    @JsonProperty("title") val title: String?,
    @JsonProperty("original_title") val originalTitle: String?,
    @JsonProperty("original_language") val originalLanguage: String?,
    @JsonProperty("id") val id: Int,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("backdrop_path") val backdropPath: String?,
    @JsonProperty("vote_average") val rating: Float?,
    @JsonProperty("vote_count") val voteCount: Int?,
    @JsonProperty("popularity") val popularity: Float?,
    @JsonProperty("overview") val overview: String?,
    @JsonProperty("genre_ids") val genreIds: List<Int>?,
    @JsonProperty("release_date") val releaseDate: Date?,
    @JsonProperty("adult") val adult: Boolean?,
    @JsonProperty("video") val video: Boolean?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("original_name") val originalName: String?,
    @JsonProperty("media_type") val mediaType: String?,
    @JsonProperty("gender") val gender: Int?,
    @JsonProperty("known_for_department") val knownForDepartment: String?,
    @JsonProperty("profile_path") val profilePath: String?,
    @JsonProperty("known_for") val knownFor: List<TmdbGenericPreview>?,
    @JsonProperty("origin_country") val originCountry: List<String>,
    @JsonProperty("first_air_date") val firstAirDate: String
)