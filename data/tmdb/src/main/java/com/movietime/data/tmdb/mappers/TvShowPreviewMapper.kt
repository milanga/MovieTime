package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.di.BackdropBaseUrl
import com.movietime.data.tmdb.di.PosterBaseUrl
import com.movietime.data.tmdb.model.TmdbTvShowPreview
import com.movietime.domain.model.TvShowPreview
import javax.inject.Inject

class TvShowPreviewMapper @Inject constructor(
    @PosterBaseUrl
    private val posterBaseUrl: String,
    @BackdropBaseUrl
    private val backdropBaseUrl: String
) {
    fun map(tmdbTvShowPreview: TmdbTvShowPreview): TvShowPreview {
        return TvShowPreview(
            adult = tmdbTvShowPreview.adult,
            backdropUrl = tmdbTvShowPreview.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            genreIds = tmdbTvShowPreview.genreIds,
            id = tmdbTvShowPreview.id,
            originCountry = tmdbTvShowPreview.originCountry,
            originalLanguage = tmdbTvShowPreview.originalLanguage,
            originalName = tmdbTvShowPreview.originalName,
            overview = tmdbTvShowPreview.overview,
            popularity = tmdbTvShowPreview.popularity,
            posterUrl = tmdbTvShowPreview.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            firstAirDate = tmdbTvShowPreview.firstAirDate,
            name = tmdbTvShowPreview.name,
            voteAverage = tmdbTvShowPreview.voteAverage,
            voteCount = tmdbTvShowPreview.voteCount
        )
    }

}