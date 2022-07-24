package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteGenre
import com.movietime.movie.model.model.Genre

object GenreMapper {
    fun map(remoteGenre: RemoteGenre): Genre =
        Genre(
            remoteGenre.id,
            remoteGenre.name
        )
}