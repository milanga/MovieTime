package com.movietime.data.tmdb.di

import com.movietime.data.tmdb.datasource.TmdbMovieDetailDataSource
import com.movietime.data.tmdb.service.MovieDetailService
import com.movietime.data.tmdb.mappers.CompanyMapper
import com.movietime.data.tmdb.mappers.CountryMapper
import com.movietime.data.tmdb.mappers.GenreMapper
import com.movietime.data.tmdb.mappers.LanguageMapper
import com.movietime.data.tmdb.mappers.MovieDetailMapper
import com.movietime.data.tmdb.mappers.VideoMapper
import com.movietime.movie.domain.repository.MovieDetailDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@InstallIn(SingletonComponent::class)
@Module
abstract class MovieDetailDataSourceModule {

    @Binds
    @Reusable
    abstract fun bindMovieDetailRemoteDataSource(tmdbMovieDetailDataSource: TmdbMovieDetailDataSource): MovieDetailDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    object MovieDetailServiceModule {

        @Provides
        @Reusable
        fun provideMovieDetailService(retrofit: Retrofit): MovieDetailService {
            return retrofit.create(MovieDetailService::class.java)
        }

        @Provides
        @Reusable
        fun provideCompanyMapper(): CompanyMapper {
            return CompanyMapper
        }

        @Provides
        @Reusable
        fun provideCountryMapper(): CountryMapper {
            return CountryMapper
        }

        @Provides
        @Reusable
        fun provideGenreMapper(): GenreMapper {
            return GenreMapper
        }

        @Provides
        @Reusable
        fun provideLanguageMapper(): LanguageMapper {
            return LanguageMapper
        }

        @Provides
        @Reusable
        fun provideVideoMapper(): VideoMapper {
            return VideoMapper
        }

        @Provides
        @Reusable
        fun provideMovieDetailMapper(
            companyMapper: CompanyMapper,
            countryMapper: CountryMapper,
            genreMapper: GenreMapper,
            languageMapper: LanguageMapper,
            @BackdropBaseUrl backdropBaseUrl: String,
            @PosterBaseUrl posterBaseUrl: String
        ): MovieDetailMapper = MovieDetailMapper(
            companyMapper,
            countryMapper,
            genreMapper,
            languageMapper,
            backdropBaseUrl,
            posterBaseUrl
        )
    }
}