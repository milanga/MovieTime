package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteMovieDetail
import com.movietime.movie.model.model.MovieDetail
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(
    private val companyMapper: CompanyMapper,
    private val countryMapper: CountryMapper,
    private val genreMapper: GenreMapper,
    private val languageMapper: LanguageMapper,
    private val backdropBaseUrl: String,
    private val posterBaseUrl: String
) {
    fun map(remoteMovieDetail: RemoteMovieDetail): MovieDetail =
        MovieDetail(
            remoteMovieDetail.adult,
            remoteMovieDetail.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            remoteMovieDetail.budget,
            remoteMovieDetail.genres.map{ genreMapper.map(it) },
            remoteMovieDetail.homepage,
            remoteMovieDetail.id,
            remoteMovieDetail.imdbId,
            remoteMovieDetail.originalLanguage,
            remoteMovieDetail.originalTitle,
            remoteMovieDetail.overview,
            remoteMovieDetail.popularity,
            remoteMovieDetail.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
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