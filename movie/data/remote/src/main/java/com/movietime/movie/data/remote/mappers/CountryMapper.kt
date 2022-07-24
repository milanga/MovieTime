package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteCountry
import com.movietime.movie.model.model.Country

object CountryMapper {
    fun map(remoteCountry: RemoteCountry): Country =
        Country(
            remoteCountry.name,
            remoteCountry.iso
        )
}