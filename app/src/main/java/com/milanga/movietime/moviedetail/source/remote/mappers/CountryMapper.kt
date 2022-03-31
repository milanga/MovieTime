package com.milanga.movietime.moviedetail.source.remote.mappers

import com.milanga.movietime.moviedetail.domain.Company
import com.milanga.movietime.moviedetail.domain.Country
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCompany
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCountry

object CountryMapper {
    fun map(remoteCountry: RemoteCountry): Country =
        Country(
            remoteCountry.name,
            remoteCountry.iso
        )
}