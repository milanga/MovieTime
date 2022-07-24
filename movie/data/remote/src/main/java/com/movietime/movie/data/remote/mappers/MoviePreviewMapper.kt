package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteMoviePreview
import com.movietime.movie.model.model.MoviePreview
import javax.inject.Inject

class MoviePreviewMapper @Inject constructor(
    private val posterBaseUrl: String,
    private val backdropBaseUrl: String
) {
    fun map(remoteMoviePreview: RemoteMoviePreview): MoviePreview {
        return MoviePreview(
            remoteMoviePreview.title,
            remoteMoviePreview.originalTitle,
            remoteMoviePreview.originalLanguage,
            remoteMoviePreview.id,
            remoteMoviePreview.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            remoteMoviePreview.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            remoteMoviePreview.rating,
            remoteMoviePreview.voteCount,
            remoteMoviePreview.popularity,
            remoteMoviePreview.overview,
            remoteMoviePreview.genreIds,
            remoteMoviePreview.releaseDate,
            remoteMoviePreview.adult,
            remoteMoviePreview.video
        )
    }
}