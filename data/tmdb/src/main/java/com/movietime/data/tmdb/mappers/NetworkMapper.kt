package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbNetwork
import com.movietime.domain.model.Network

object NetworkMapper {
    fun map(tmdbNetwork: TmdbNetwork): Network =
        Network(
            tmdbNetwork.id,
            tmdbNetwork.logoPath,
            tmdbNetwork.name,
            tmdbNetwork.originCountry
        )
}