package com.milanga.movietime.movies.source.remote.mappers

import com.milanga.movietime.movies.domain.MoviePreview
import com.milanga.movietime.movies.source.remote.model.RemoteMoviePreview

object MoviePreviewMapper {
    fun map(remoteMoviePreview: RemoteMoviePreview): MoviePreview {
        return MoviePreview(
            remoteMoviePreview.title,
            remoteMoviePreview.originalTitle,
            remoteMoviePreview.originalLanguage,
            remoteMoviePreview.id,
            remoteMoviePreview.posterPath,
            remoteMoviePreview.backdropPath,
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