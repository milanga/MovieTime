package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbGenre
import com.movietime.movie.domain.model.Genre

object GenreMapper {
    fun map(tmdbGenre: TmdbGenre): Genre =
        Genre(
            tmdbGenre.id,
            tmdbGenre.name
        )
}