package com.movietime.data.trakt.mappers

import com.movietime.data.trakt.model.TraktIds
import com.movietime.domain.model.MediaIds

object MediaIdsMapper {
    fun map(traktIds: TraktIds): MediaIds =
        MediaIds(
            trakt = traktIds.trakt!!,
            slug = traktIds.slug!!,
            imdb = traktIds.imdb,
            tmdb = traktIds.tmdb!!,
            tvdb = traktIds.tvdb
        )
}