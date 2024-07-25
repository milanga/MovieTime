package com.movietime.data.tmdb.di.mappers

import com.movietime.data.tmdb.mappers.CompanyMapper
import com.movietime.data.tmdb.mappers.CountryMapper
import com.movietime.data.tmdb.mappers.CreatorMapper
import com.movietime.data.tmdb.mappers.EpisodeMapper
import com.movietime.data.tmdb.mappers.GenreMapper
import com.movietime.data.tmdb.mappers.LanguageMapper
import com.movietime.data.tmdb.mappers.NetworkMapper
import com.movietime.data.tmdb.mappers.SeasonMapper
import com.movietime.data.tmdb.mappers.VideoMapper
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object MappersModule {
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
    fun provideCreatorMapper(): CreatorMapper {
        return CreatorMapper
    }

    @Provides
    @Reusable
    fun provideEpisodeMapper(): EpisodeMapper {
        return EpisodeMapper
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
    fun provideNetworkMapper(): NetworkMapper {
        return NetworkMapper
    }

    @Provides
    @Reusable
    fun provideSeasonMapper(): SeasonMapper {
        return SeasonMapper
    }

    @Provides
    @Reusable
    fun provideVideoMapper(): VideoMapper {
        return VideoMapper
    }
}