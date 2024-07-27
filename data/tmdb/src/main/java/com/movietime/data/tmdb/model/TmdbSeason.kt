package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbSeason(
    @JsonProperty("air_date") val airDate: String?,
    @JsonProperty("episode_count") val episodeCount: Int,
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("overview") val overview: String,
    @JsonProperty("poster_path") val posterPath: String?,
    @JsonProperty("season_number") val seasonNumber: Int,
    @JsonProperty("vote_average") val voteAverage: Double
)
