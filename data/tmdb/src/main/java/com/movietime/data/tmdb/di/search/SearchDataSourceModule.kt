package com.movietime.data.tmdb.di.search

import com.movietime.data.tmdb.datasource.TmdbSearchDataSource
import com.movietime.data.tmdb.di.network.NetworkModule.Companion.TMDB_RETROFIT
import com.movietime.data.tmdb.service.SearchService
import com.movietime.domain.repository.search.SearchDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named


@InstallIn(SingletonComponent::class)
@Module
abstract class SearchDataSourceModule {

    @Binds
    @Reusable
    abstract fun bindTmdbSearchDataSource(tmdbSearchDataSource: TmdbSearchDataSource): SearchDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    object SearchServiceModule {

        @Provides
        @Reusable
        fun provideSearchService(@Named(TMDB_RETROFIT) retrofit: Retrofit): SearchService {
            return retrofit.create(SearchService::class.java)
        }
    }
}