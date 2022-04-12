package com.movietime.movietime.moviedetail.source.remote.mappers

import com.movietime.movietime.moviedetail.domain.*
import com.movietime.movietime.moviedetail.source.remote.model.*
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(
    private val companyMapper: CompanyMapper,
    private val countryMapper: CountryMapper,
    private val genreMapper: GenreMapper,
    private val languageMapper: LanguageMapper
) {
    fun map(remoteMovieDetail: RemoteMovieDetail): MovieDetail =
        MovieDetail(
            remoteMovieDetail.adult,
            remoteMovieDetail.backdropPath,
            remoteMovieDetail.budget,
            remoteMovieDetail.genres.map{ genreMapper.map(it) },
            remoteMovieDetail.homepage,
            remoteMovieDetail.id,
            remoteMovieDetail.imdbId,
            remoteMovieDetail.originalLanguage,
            remoteMovieDetail.originalTitle,
            remoteMovieDetail.overview,
            remoteMovieDetail.popularity,
            remoteMovieDetail.posterPath,
            remoteMovieDetail.productionCompanies.map{ companyMapper.map(it) },
            remoteMovieDetail.productionCountries.map{ countryMapper.map(it) },
            remoteMovieDetail.releaseDate,
            remoteMovieDetail.revenue,
            remoteMovieDetail.runtime,
            remoteMovieDetail.spokeLanguages.map{ languageMapper.map(it) },
            remoteMovieDetail.status,
            remoteMovieDetail.tagline,
            remoteMovieDetail.title,
            remoteMovieDetail.video,
            remoteMovieDetail.voteAverage,
            remoteMovieDetail.voteCount
        )
}