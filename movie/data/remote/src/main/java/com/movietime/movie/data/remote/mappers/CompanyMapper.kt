package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteCompany
import com.movietime.movie.domain.model.Company


object CompanyMapper {
    fun map(remoteCompany: RemoteCompany): Company =
        Company(
            remoteCompany.name,
            remoteCompany.id,
            remoteCompany.logoPath,
            remoteCompany.originCountry
        )
}