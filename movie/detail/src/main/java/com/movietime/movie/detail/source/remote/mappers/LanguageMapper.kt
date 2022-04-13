package com.movietime.movie.detail.source.remote.mappers

import com.movietime.movie.detail.source.remote.model.RemoteLanguage
import com.movietime.movie.domain.detail.Language

object LanguageMapper {
    fun map(remoteLanguage: RemoteLanguage): Language =
        Language(
            remoteLanguage.englishName,
            remoteLanguage.name,
            remoteLanguage.iso
        )
}