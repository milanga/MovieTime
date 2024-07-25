package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.di.baseurls.BackdropBaseUrl
import com.movietime.data.tmdb.di.baseurls.PosterBaseUrl
import com.movietime.data.tmdb.model.TmdbTvShowDetail
import com.movietime.domain.model.TvShowDetail
import javax.inject.Inject

class TvShowDetailMapper @Inject constructor(
    private val companyMapper: CompanyMapper,
    private val countryMapper: CountryMapper,
    private val genreMapper: GenreMapper,
    private val languageMapper: LanguageMapper,
    private val creatorMapper: CreatorMapper,
    private val episodeMapper: EpisodeMapper,
    private val networkMapper: NetworkMapper,
    private val seasonMapper: SeasonMapper,
    @BackdropBaseUrl
    private val backdropBaseUrl: String,
    @PosterBaseUrl
    private val posterBaseUrl: String
) {
    fun map(tmdbTvShowDetail: TmdbTvShowDetail): TvShowDetail =
        TvShowDetail(
            adult = tmdbTvShowDetail.adult,
            backdropUrl = tmdbTvShowDetail.backdropPath?.let{ backdropPath -> "$backdropBaseUrl${backdropPath}" } ?: "",
            createdBy = tmdbTvShowDetail.createdBy.map { creatorMapper.map(it) },
            episodeRunTime = tmdbTvShowDetail.episodeRunTime,
            firstAirDate = tmdbTvShowDetail.firstAirDate,
            genres = tmdbTvShowDetail.genres.map { genreMapper.map(it) },
            homepage = tmdbTvShowDetail.homepage,
            id = tmdbTvShowDetail.id,
            inProduction = tmdbTvShowDetail.inProduction,
            languages = tmdbTvShowDetail.languages,
            lastAirDate = tmdbTvShowDetail.lastAirDate,
            lastEpisodeToAir = episodeMapper.map(tmdbTvShowDetail.lastEpisodeToAir),
            name = tmdbTvShowDetail.name,
            nextEpisodeToAir = tmdbTvShowDetail.nextEpisodeToAir?.let { episodeMapper.map(it) },
            networks = tmdbTvShowDetail.networks.map { networkMapper.map(it) },
            numberOfEpisodes = tmdbTvShowDetail.numberOfEpisodes,
            numberOfSeasons = tmdbTvShowDetail.numberOfSeasons,
            originCountry = tmdbTvShowDetail.originCountry,
            originalLanguage = tmdbTvShowDetail.originalLanguage,
            originalName = tmdbTvShowDetail.originalName,
            overview = tmdbTvShowDetail.overview,
            popularity = tmdbTvShowDetail.popularity,
            posterUrl = tmdbTvShowDetail.posterPath?.let{ posterPath -> "$posterBaseUrl${posterPath}" } ?: "",
            productionCompanies = tmdbTvShowDetail.productionCompanies.map { companyMapper.map(it) },
            productionCountries = tmdbTvShowDetail.productionCountries.map { countryMapper.map(it) },
            seasons = tmdbTvShowDetail.seasons.map { seasonMapper.map(it) },
            spokenLanguages = tmdbTvShowDetail.spokenLanguages.map { languageMapper.map(it) },
            status = tmdbTvShowDetail.status,
            tagline = tmdbTvShowDetail.tagline,
            type = tmdbTvShowDetail.type,
            voteAverage = tmdbTvShowDetail.voteAverage,
            voteCount = tmdbTvShowDetail.voteCount
        )
}