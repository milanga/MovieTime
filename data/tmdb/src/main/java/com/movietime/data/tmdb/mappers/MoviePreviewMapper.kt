package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.di.baseurls.BackdropBaseUrl
import com.movietime.data.tmdb.di.baseurls.PosterBaseUrl
import com.movietime.data.tmdb.model.MEDIA_TYPE_MOVIE
import com.movietime.data.tmdb.model.TmdbGenericPreview
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

    fun map(tmdbGenericPreview: TmdbGenericPreview): MoviePreview {
        require(tmdbGenericPreview.mediaType == MEDIA_TYPE_MOVIE)
        return MoviePreview(
            tmdbGenericPreview.title!!,
            tmdbGenericPreview.originalTitle!!,
            tmdbGenericPreview.originalLanguage!!,
            tmdbGenericPreview.id,
            tmdbGenericPreview.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            tmdbGenericPreview.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            tmdbGenericPreview.rating!!,
            tmdbGenericPreview.voteCount!!,
            tmdbGenericPreview.popularity!!,
            tmdbGenericPreview.overview!!,
            tmdbGenericPreview.genreIds!!,
            tmdbGenericPreview.releaseDate,
            tmdbGenericPreview.adult!!,
            tmdbGenericPreview.video!!
        )
    }
}