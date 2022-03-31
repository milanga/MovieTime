package com.milanga.movietime.moviedetail.source.remote.mappers

import com.milanga.movietime.moviedetail.domain.Company
import com.milanga.movietime.moviedetail.domain.Country
import com.milanga.movietime.moviedetail.domain.Genre
import com.milanga.movietime.moviedetail.domain.Language
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCompany
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCountry
import com.milanga.movietime.moviedetail.source.remote.model.RemoteGenre
import com.milanga.movietime.moviedetail.source.remote.model.RemoteLanguage

object LanguageMapper {
    fun map(remoteLanguage: RemoteLanguage): Language =
        Language(
            remoteLanguage.englishName,
            remoteLanguage.name,
            remoteLanguage.iso
        )
}