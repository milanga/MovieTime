package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbLastEpisodeToAir(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("overview") val overview: String,
    @JsonProperty("vote_average") val voteAverage: Double,
    @JsonProperty("vote_count") val voteCount: Int,
    @JsonProperty("air_date") val airDate: String,
    @JsonProperty("episode_number") val episodeNumber: Int,
    @JsonProperty("episode_type") val episodeType: String,
    @JsonProperty("production_code") val productionCode: String,
    @JsonProperty("runtime") val runtime: Int,
    @JsonProperty("season_number") val seasonNumber: Int,
    @JsonProperty("show_id") val showId: Int,
    @JsonProperty("still_path") val stillPath: String
)