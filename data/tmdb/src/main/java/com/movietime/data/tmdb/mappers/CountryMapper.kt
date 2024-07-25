package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbCountry
import com.movietime.movie.domain.model.Country

object CountryMapper {
    fun map(tmdbCountry: TmdbCountry): Country =
        Country(
            tmdbCountry.name,
            tmdbCountry.iso
        )
}