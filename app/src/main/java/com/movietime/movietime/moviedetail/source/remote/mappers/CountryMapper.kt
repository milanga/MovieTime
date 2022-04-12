package com.movietime.movietime.moviedetail.source.remote.mappers

import com.movietime.movietime.moviedetail.domain.Country
import com.movietime.movietime.moviedetail.source.remote.model.RemoteCountry

object CountryMapper {
    fun map(remoteCountry: RemoteCountry): Country =
        Country(
            remoteCountry.name,
            remoteCountry.iso
        )
}