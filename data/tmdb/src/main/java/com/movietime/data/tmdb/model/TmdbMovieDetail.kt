package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties("belongs_to_collection", "origin_country")
data class TmdbMovieDetail(
    @JsonProperty("adult") val adult: Boolean,
    @JsonProperty("backdrop_path") val backdropPath: String?,
    @JsonProperty("budget") val budget: Int,
    @JsonProperty("genres") val genres: List<TmdbGenre>,
    @JsonProperty("homepage") val homepage: String?,
    @JsonProperty("id") val id: Int,
    @JsonProperty("imdb_id") val imdbId: String?,
    @JsonProperty("original_language") val originalLanguage: String,
    @JsonProperty("original_title") val originalTitle: String,
    @JsonProperty("overview") val overview: String,
    @JsonProperty("popularity") val popularity: Double,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("production_companies") val productionCompanies: List<TmdbCompany>,
    @JsonProperty("production_countries") val productionCountries: List<TmdbCountry>,
    @JsonProperty("release_date") val releaseDate: String,
    @JsonProperty("revenue") val revenue: Long,
    @JsonProperty("runtime") val runtime: Int?,
    @JsonProperty("spoken_languages") val spokeLanguages: List<TmdbLanguage>,
    @JsonProperty("status") val status: String,
    @JsonProperty("tagline") val tagline: String?,
    @JsonProperty("title") val title: String,
    @JsonProperty("video") val video: Boolean,
    @JsonProperty("vote_average") val voteAverage: Double,
    @JsonProperty("vote_count") val voteCount: Int,
)