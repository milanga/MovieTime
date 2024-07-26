package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.di.baseurls.BackdropBaseUrl
import com.movietime.data.tmdb.di.baseurls.PosterBaseUrl
import com.movietime.data.tmdb.model.MEDIA_TYPE_TV
import com.movietime.data.tmdb.model.TmdbGenericPreview
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

    fun map(tmdbGenericPreview: TmdbGenericPreview): TvShowPreview {
        require(tmdbGenericPreview.mediaType == MEDIA_TYPE_TV)
        return TvShowPreview(
            adult = tmdbGenericPreview.adult!!,
            backdropUrl = tmdbGenericPreview.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            genreIds = tmdbGenericPreview.genreIds!!,
            id = tmdbGenericPreview.id,
            originCountry = tmdbGenericPreview.originCountry,
            originalLanguage = tmdbGenericPreview.originalLanguage!!,
            originalName = tmdbGenericPreview.originalName!!,
            overview = tmdbGenericPreview.overview!!,
            popularity = tmdbGenericPreview.popularity!!,
            posterUrl = tmdbGenericPreview.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            firstAirDate = tmdbGenericPreview.firstAirDate,
            name = tmdbGenericPreview.name!!,
            voteAverage = tmdbGenericPreview.rating!!,
            voteCount = tmdbGenericPreview.voteCount!!
        )
    }

}