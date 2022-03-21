package com.milanga.movietime.movies

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

@JsonIgnoreProperties("media_type")
data class MoviePreview(
    @JsonProperty("title") val title: String,
    @JsonProperty("original_title") val originalTitle: String,
    @JsonProperty("original_language") val originalLanguage: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("poster_path") private val posterPath: String?,
    @JsonProperty("backdrop_path") private val backdropPath: String?,
    @JsonProperty("vote_average") private val rating: Float,
    @JsonProperty("vote_count") val voteCount: String,
    @JsonProperty("popularity") val popularity: String,
    @JsonProperty("overview") val overview: String,
    @JsonProperty("genre_ids") val genreIds: List<Int>,
    @JsonProperty("release_date") val releaseDate: Date,
    @JsonProperty("adult") val adult: Boolean,
    @JsonProperty("video") val video: Boolean,
) {
    companion object {
        private const val POSTER_BASE_PATH = "http://image.tmdb.org/t/p/w500"
        private const val BACKDROP_BASE_PATH = "http://image.tmdb.org/t/p/original"
    }

    fun getPosterUrl(): String {
        return "$POSTER_BASE_PATH$posterPath"
    }

    fun getBackdropUrl(): String {
        return "$BACKDROP_BASE_PATH$backdropPath"
    }

    fun getRating(): Float {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.HALF_EVEN

        return df.format(rating).toFloat()
    }

}