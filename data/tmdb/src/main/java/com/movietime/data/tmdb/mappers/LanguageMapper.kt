package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbLanguage
import com.movietime.movie.domain.model.Language

object LanguageMapper {
    fun map(tmdbLanguage: TmdbLanguage): Language =
        Language(
            tmdbLanguage.englishName,
            tmdbLanguage.name,
            tmdbLanguage.iso
        )
}