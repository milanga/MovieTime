package com.movietime.movietime.moviedetail.source.remote.mappers

import com.movietime.movie.domain.detail.Company
import com.movietime.movietime.moviedetail.source.remote.model.RemoteCompany

object CompanyMapper {
    fun map(remoteCompany: RemoteCompany): Company =
        Company(
            remoteCompany.name,
            remoteCompany.id,
            remoteCompany.logoPath,
            remoteCompany.originCountry
        )
}