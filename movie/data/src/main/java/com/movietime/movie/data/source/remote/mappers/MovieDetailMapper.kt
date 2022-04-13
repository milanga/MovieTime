package com.movietime.movie.data.source.remote.mappers

import com.movietime.movie.data.source.remote.model.RemoteMovieDetail
import com.movietime.movie.domain.detail.MovieDetail
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
            remoteMovieDetail.genres.map{ GenreMapper.map(it) },
            remoteMovieDetail.homepage,
            remoteMovieDetail.id,
            remoteMovieDetail.imdbId,
            remoteMovieDetail.originalLanguage,
            remoteMovieDetail.originalTitle,
            remoteMovieDetail.overview,
            remoteMovieDetail.popularity,
            remoteMovieDetail.posterPath,
            remoteMovieDetail.productionCompanies.map{ CompanyMapper.map(it) },
            remoteMovieDetail.productionCountries.map{ CountryMapper.map(it) },
            remoteMovieDetail.releaseDate,
            remoteMovieDetail.revenue,
            remoteMovieDetail.runtime,
            remoteMovieDetail.spokeLanguages.map{ LanguageMapper.map(it) },
            remoteMovieDetail.status,
            remoteMovieDetail.tagline,
            remoteMovieDetail.title,
            remoteMovieDetail.video,
            remoteMovieDetail.voteAverage,
            remoteMovieDetail.voteCount
        )
}