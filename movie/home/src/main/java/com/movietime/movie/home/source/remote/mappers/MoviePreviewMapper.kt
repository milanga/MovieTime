package com.movietime.movie.home.source.remote.mappers

import com.movietime.movie.domain.MoviePreview
import com.movietime.movie.home.source.remote.model.RemoteMoviePreview

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