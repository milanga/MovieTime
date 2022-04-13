package com.movietime.movietime.moviedetail.source.remote.mappers

import com.movietime.movie.domain.detail.Country
import com.movietime.movietime.moviedetail.source.remote.model.RemoteCountry

object CountryMapper {
    fun map(remoteCountry: RemoteCountry): Country =
        Country(
            remoteCountry.name,
            remoteCountry.iso
        )
}