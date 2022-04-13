package com.movietime.movie.data.source.remote.mappers

import com.movietime.movie.data.source.remote.model.RemoteCompany
import com.movietime.movie.domain.detail.Company

object CompanyMapper {
    fun map(remoteCompany: RemoteCompany): Company =
        Company(
            remoteCompany.name,
            remoteCompany.id,
            remoteCompany.logoPath,
            remoteCompany.originCountry
        )
}