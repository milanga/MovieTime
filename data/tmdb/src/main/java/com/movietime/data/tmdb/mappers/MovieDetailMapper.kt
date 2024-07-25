package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.di.BackdropBaseUrl
import com.movietime.data.tmdb.di.PosterBaseUrl
import com.movietime.data.tmdb.model.TmdbMovieDetail
import com.movietime.movie.domain.model.MovieDetail
import javax.inject.Inject

class MovieDetailMapper @Inject constructor(
    private val companyMapper: CompanyMapper,
    private val countryMapper: CountryMapper,
    private val genreMapper: GenreMapper,
    private val languageMapper: LanguageMapper,
    @BackdropBaseUrl
    private val backdropBaseUrl: String,
    @PosterBaseUrl
    private val posterBaseUrl: String
) {
    fun map(tmdbMovieDetail: TmdbMovieDetail): MovieDetail =
        MovieDetail(
            tmdbMovieDetail.adult,
            tmdbMovieDetail.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            tmdbMovieDetail.budget,
            tmdbMovieDetail.genres.map{ genreMapper.map(it) },
            tmdbMovieDetail.homepage,
            tmdbMovieDetail.id,
            tmdbMovieDetail.imdbId,
            tmdbMovieDetail.originalLanguage,
            tmdbMovieDetail.originalTitle,
            tmdbMovieDetail.overview,
            tmdbMovieDetail.popularity,
            tmdbMovieDetail.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            tmdbMovieDetail.productionCompanies.map{ companyMapper.map(it) },
            tmdbMovieDetail.productionCountries.map{ countryMapper.map(it) },
            tmdbMovieDetail.releaseDate,
            tmdbMovieDetail.revenue,
            tmdbMovieDetail.runtime,
            tmdbMovieDetail.spokeLanguages.map{ languageMapper.map(it) },
            tmdbMovieDetail.status,
            tmdbMovieDetail.tagline,
            tmdbMovieDetail.title,
            tmdbMovieDetail.video,
            tmdbMovieDetail.voteAverage,
            tmdbMovieDetail.voteCount
        )
}