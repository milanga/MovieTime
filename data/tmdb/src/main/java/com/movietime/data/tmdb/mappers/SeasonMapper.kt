package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbSeason
import com.movietime.domain.model.Season

object SeasonMapper {
    fun map(tmdbSeason: TmdbSeason): Season =
        Season(
            tmdbSeason.airDate,
            tmdbSeason.episodeCount,
            tmdbSeason.id,
            tmdbSeason.name,
            tmdbSeason.overview,
            tmdbSeason.posterPath,
            tmdbSeason.seasonNumber,
            tmdbSeason.voteAverage
        )
}