package com.movietime.data.trakt.mappers

import com.movietime.data.trakt.model.TraktIds
import com.movietime.domain.model.MovieIds

object MovieIdsMapper {
    fun map(traktIds: TraktIds): MovieIds =
        MovieIds(
            traktIds.trakt,
            traktIds.slug,
            traktIds.imdb,
            traktIds.tmdb
        )
}