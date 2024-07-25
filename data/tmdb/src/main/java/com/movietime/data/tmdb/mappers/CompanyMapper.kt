package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbCompany
import com.movietime.domain.model.Company


object CompanyMapper {
    fun map(tmdbCompany: TmdbCompany): Company =
        Company(
            tmdbCompany.name,
            tmdbCompany.id,
            tmdbCompany.logoPath,
            tmdbCompany.originCountry
        )
}