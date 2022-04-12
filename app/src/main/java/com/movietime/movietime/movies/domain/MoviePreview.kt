package com.movietime.movietime.movies.domain

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

data class MoviePreview(
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val id: Int,
    private val posterPath: String?,
    private val backdropPath: String?,
    private val rating: Float,
    val voteCount: String,
    val popularity: String,
    val overview: String,
    val genreIds: List<Int>,
    val releaseDate: Date?,
    val adult: Boolean,
    val video: Boolean,
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