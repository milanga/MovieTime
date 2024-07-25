package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbShowCreator
import com.movietime.domain.model.ShowCreator

object CreatorMapper {
    fun map(tmdbShowCreator: TmdbShowCreator): ShowCreator =
        ShowCreator(
            tmdbShowCreator.id,
            tmdbShowCreator.creditId,
            tmdbShowCreator.name,
            tmdbShowCreator.originalName,
            tmdbShowCreator.gender,
            tmdbShowCreator.profilePath
        )
}