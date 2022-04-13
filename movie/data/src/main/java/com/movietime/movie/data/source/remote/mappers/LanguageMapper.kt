package com.movietime.movie.data.source.remote.mappers

import com.movietime.movie.data.source.remote.model.RemoteLanguage
import com.movietime.movie.domain.detail.Language

object LanguageMapper {
    fun map(remoteLanguage: RemoteLanguage): Language =
        Language(
            remoteLanguage.englishName,
            remoteLanguage.name,
            remoteLanguage.iso
        )
}