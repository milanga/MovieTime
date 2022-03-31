package com.milanga.movietime.moviedetail.domain

data class MovieDetail(
    val adult: Boolean,
    private val backdropPath: String?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<Company>,
    val productionCountries: List<Country>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int?,
    val spokeLanguages: List<Language>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
){
    companion object {
        private const val BACKDROP_BASE_PATH = "http://image.tmdb.org/t/p/original"
    }

    fun getBackdropUrl(): String {
        return "$BACKDROP_BASE_PATH$backdropPath"
    }
}