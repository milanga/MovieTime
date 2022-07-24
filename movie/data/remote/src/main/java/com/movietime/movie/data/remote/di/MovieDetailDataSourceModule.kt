package com.movietime.movie.data.remote.di

import com.movietime.movie.data.remote.MovieDetailRemoteDataSource
import com.movietime.movie.data.remote.MovieDetailService
import com.movietime.movie.data.remote.mappers.*
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
    abstract fun bindMovieDetailRemoteDataSource(movieDetailRemoteDataSource: MovieDetailRemoteDataSource): MovieDetailDataSource

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