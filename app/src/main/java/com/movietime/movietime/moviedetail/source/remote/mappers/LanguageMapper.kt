package com.movietime.movietime.moviedetail.source.remote.mappers

import com.movietime.movietime.moviedetail.domain.Language
import com.movietime.movietime.moviedetail.source.remote.model.RemoteLanguage

object LanguageMapper {
    fun map(remoteLanguage: RemoteLanguage): Language =
        Language(
            remoteLanguage.englishName,
            remoteLanguage.name,
            remoteLanguage.iso
        )
}