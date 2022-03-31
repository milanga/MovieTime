package com.milanga.movietime.moviedetail.source.remote.mappers

import com.milanga.movietime.moviedetail.domain.Company
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCompany

object CompanyMapper {
    fun map(remoteCompany: RemoteCompany): Company =
        Company(
            remoteCompany.name,
            remoteCompany.id,
            remoteCompany.logoPath,
            remoteCompany.originCountry
        )
}