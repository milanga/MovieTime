package com.movietime.movie.detail.source.remote.mappers

import com.movietime.movie.detail.source.remote.model.RemoteCountry
import com.movietime.movie.domain.detail.Country

object CountryMapper {
    fun map(remoteCountry: RemoteCountry): Country =
        Country(
            remoteCountry.name,
            remoteCountry.iso
        )
}