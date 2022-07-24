package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteLanguage
import com.movietime.movie.model.model.Language

object LanguageMapper {
    fun map(remoteLanguage: RemoteLanguage): Language =
        Language(
            remoteLanguage.englishName,
            remoteLanguage.name,
            remoteLanguage.iso
        )
}