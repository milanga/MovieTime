package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbEpisode
import com.movietime.domain.model.Episode

object EpisodeMapper {
    fun map(tmdbEpisode: TmdbEpisode): Episode =
        Episode(
            tmdbEpisode.id,
            tmdbEpisode.name,
            tmdbEpisode.overview,
            tmdbEpisode.voteAverage,
            tmdbEpisode.voteCount,
            tmdbEpisode.airDate,
            tmdbEpisode.episodeNumber,
            tmdbEpisode.episodeType,
            tmdbEpisode.productionCode,
            tmdbEpisode.runtime,
            tmdbEpisode.seasonNumber,
            tmdbEpisode.showId,
            tmdbEpisode.stillPath
        )
}