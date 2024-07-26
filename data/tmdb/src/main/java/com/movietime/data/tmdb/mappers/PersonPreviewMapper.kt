package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.di.baseurls.PosterBaseUrl
import com.movietime.data.tmdb.model.TmdbGenericPreview
import com.movietime.data.tmdb.model.TmdbPersonPreview
import com.movietime.domain.model.PersonPreview
import javax.inject.Inject

class PersonPreviewMapper @Inject constructor(
    @PosterBaseUrl
    private val posterBaseUrl: String,
    private val mediaPreviewMapper: MediaPreviewMapper
) {
    fun map(tmdbPersonPreview: TmdbPersonPreview): PersonPreview {
        return PersonPreview(
            tmdbPersonPreview.id,
            tmdbPersonPreview.name,
            tmdbPersonPreview.originalName,
            tmdbPersonPreview.mediaType,
            tmdbPersonPreview.adult,
            tmdbPersonPreview.popularity,
            tmdbPersonPreview.gender,
            tmdbPersonPreview.knownForDepartment,
            tmdbPersonPreview.profilePath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            tmdbPersonPreview.knownFor.map { mediaPreviewMapper.map(it) }
        )
    }

    fun map(tmdbGenericPreview: TmdbGenericPreview): PersonPreview {
        require(tmdbGenericPreview.mediaType == "person")
        return PersonPreview(
            tmdbGenericPreview.id,
            tmdbGenericPreview.name!!,
            tmdbGenericPreview.originalName!!,
            tmdbGenericPreview.mediaType!!,
            tmdbGenericPreview.adult!!,
            tmdbGenericPreview.popularity!!,
            tmdbGenericPreview.gender!!,
            tmdbGenericPreview.knownForDepartment!!,
            tmdbGenericPreview.profilePath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            tmdbGenericPreview.knownFor!!.map { mediaPreviewMapper.map(it) }
        )
    }
}