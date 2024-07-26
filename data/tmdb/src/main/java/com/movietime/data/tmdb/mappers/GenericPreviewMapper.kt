package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.MEDIA_TYPE_MOVIE
import com.movietime.data.tmdb.model.MEDIA_TYPE_PERSON
import com.movietime.data.tmdb.model.MEDIA_TYPE_TV
import com.movietime.data.tmdb.model.TmdbGenericPreview
import com.movietime.domain.model.GenericPreview
import javax.inject.Inject

class GenericPreviewMapper @Inject constructor(
    private val mediaPreviewMapper: MediaPreviewMapper,
    private val personPreviewMapper: PersonPreviewMapper
) {
    fun map(tmdbGenericPreview: TmdbGenericPreview): GenericPreview {
        return when (tmdbGenericPreview.mediaType) {
            MEDIA_TYPE_MOVIE,
            MEDIA_TYPE_TV -> mediaPreviewMapper.map(tmdbGenericPreview)
            MEDIA_TYPE_PERSON -> personPreviewMapper.map(tmdbGenericPreview)
            else -> throw IllegalArgumentException("Unknown media type")
        }
    }
}