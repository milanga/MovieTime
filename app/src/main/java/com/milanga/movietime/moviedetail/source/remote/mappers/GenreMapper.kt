package com.milanga.movietime.moviedetail.source.remote.mappers

import com.milanga.movietime.moviedetail.domain.Company
import com.milanga.movietime.moviedetail.domain.Country
import com.milanga.movietime.moviedetail.domain.Genre
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCompany
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCountry
import com.milanga.movietime.moviedetail.source.remote.model.RemoteGenre

object GenreMapper {
    fun map(remoteGenre: RemoteGenre): Genre =
        Genre(
            remoteGenre.id,
            remoteGenre.name
        )
}