package com.movietime.data.tmdb.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TmdbTvShowDetail(
    @JsonProperty("adult") val adult: Boolean,
    @JsonProperty("backdrop_path") val backdropPath: String,
    @JsonProperty("created_by") val createdBy: List<TmdbShowCreator>,
    @JsonProperty("episode_run_time") val episodeRunTime: List<Int>,
    @JsonProperty("first_air_date") val firstAirDate: String,
    @JsonProperty("genres") val genres: List<TmdbGenre>,
    @JsonProperty("homepage") val homepage: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("in_production") val inProduction: Boolean,
    @JsonProperty("languages") val languages: List<String>,
    @JsonProperty("last_air_date") val lastAirDate: String,
    @JsonProperty("last_episode_to_air") val lastEpisodeToAir: TmdbLastEpisodeToAir,
    @JsonProperty("name") val name: String,
    @JsonProperty("next_episode_to_air") val nextEpisodeToAir: Any?,
    @JsonProperty("networks") val networks: List<TmdbNetwork>,
    @JsonProperty("number_of_episodes") val numberOfEpisodes: Int,
    @JsonProperty("number_of_seasons") val numberOfSeasons: Int,
    @JsonProperty("origin_country") val originCountry: List<String>,
    @JsonProperty("original_language") val originalLanguage: String,
    @JsonProperty("original_name") val originalName: String,
    @JsonProperty("overview") val overview: String,
    @JsonProperty("popularity") val popularity: Double,
    @JsonProperty("poster_path") val posterPath: String,
    @JsonProperty("production_companies") val productionCompanies: List<TmdbCompany>,
    @JsonProperty("production_countries") val productionCountries: List<TmdbCountry>,
    @JsonProperty("seasons") val seasons: List<TmdbSeason>,
    @JsonProperty("spoken_languages") val spokenLanguages: List<TmdbLanguage>,
    @JsonProperty("status") val status: String,
    @JsonProperty("tagline") val tagline: String,
    @JsonProperty("type") val type: String,
    @JsonProperty("vote_average") val voteAverage: Double,
    @JsonProperty("vote_count") val voteCount: Int
)