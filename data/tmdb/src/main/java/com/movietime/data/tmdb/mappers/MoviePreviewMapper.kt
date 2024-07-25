package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.di.BackdropBaseUrl
import com.movietime.data.tmdb.di.PosterBaseUrl
import com.movietime.data.tmdb.model.TmdbMoviePreview
import com.movietime.domain.model.MoviePreview
import javax.inject.Inject

class MoviePreviewMapper @Inject constructor(
    @PosterBaseUrl
    private val posterBaseUrl: String,
    @BackdropBaseUrl
    private val backdropBaseUrl: String
) {
    fun map(tmdbMoviePreview: TmdbMoviePreview): MoviePreview {
        return MoviePreview(
            tmdbMoviePreview.title,
            tmdbMoviePreview.originalTitle,
            tmdbMoviePreview.originalLanguage,
            tmdbMoviePreview.id,
            tmdbMoviePreview.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            tmdbMoviePreview.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            tmdbMoviePreview.rating,
            tmdbMoviePreview.voteCount,
            tmdbMoviePreview.popularity,
            tmdbMoviePreview.overview,
            tmdbMoviePreview.genreIds,
            tmdbMoviePreview.releaseDate,
            tmdbMoviePreview.adult,
            tmdbMoviePreview.video
        )
    }
}