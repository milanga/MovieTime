package com.movietime.movie.detail.source.remote.mappers

import com.movietime.movie.detail.source.remote.model.RemoteGenre
import com.movietime.movie.domain.detail.Genre

object GenreMapper {
    fun map(remoteGenre: RemoteGenre): Genre =
        Genre(
            remoteGenre.id,
            remoteGenre.name
        )
}