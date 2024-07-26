package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.MEDIA_TYPE_MOVIE
import com.movietime.data.tmdb.model.MEDIA_TYPE_TV
import com.movietime.data.tmdb.model.TmdbGenericPreview
import com.movietime.domain.model.MediaPreview
import javax.inject.Inject

class MediaPreviewMapper @Inject constructor(
    private val tvShowPreviewMapper: TvShowPreviewMapper,
    private val moviePreviewMapper: MoviePreviewMapper,
) {
    fun map(tmdbGenericPreview: TmdbGenericPreview): MediaPreview {
        return when (tmdbGenericPreview.mediaType) {
            MEDIA_TYPE_MOVIE -> moviePreviewMapper.map(tmdbGenericPreview)
            MEDIA_TYPE_TV -> tvShowPreviewMapper.map(tmdbGenericPreview)
            else -> throw IllegalArgumentException("Unknown media type")
        }
    }
}