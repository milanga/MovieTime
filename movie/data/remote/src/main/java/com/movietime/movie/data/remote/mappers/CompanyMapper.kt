package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteCompany
import com.movietime.movie.model.model.Company


object CompanyMapper {
    fun map(remoteCompany: RemoteCompany): Company =
        Company(
            remoteCompany.name,
            remoteCompany.id,
            remoteCompany.logoPath,
            remoteCompany.originCountry
        )
}