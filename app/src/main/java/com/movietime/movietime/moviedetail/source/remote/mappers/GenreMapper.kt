package com.movietime.movietime.moviedetail.source.remote.mappers

import com.movietime.movietime.moviedetail.domain.Genre
import com.movietime.movietime.moviedetail.source.remote.model.RemoteGenre

object GenreMapper {
    fun map(remoteGenre: RemoteGenre): Genre =
        Genre(
            remoteGenre.id,
            remoteGenre.name
        )
}