package com.movietime.data.tmdb.di.tvshow

import com.movietime.data.tmdb.datasource.TmdbTvShowsDataSource
import com.movietime.data.tmdb.di.network.NetworkModule.Companion.TMDB_RETROFIT
import com.movietime.data.tmdb.service.TvShowsService
import com.movietime.domain.repository.tvshow.TvShowsDataSource
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
abstract class TvShowRemoteSourceModule {

    @Binds
    @Reusable
    abstract fun bindTvShowsRemoteDataSource(tmdbTvShowsDataSource: TmdbTvShowsDataSource): TvShowsDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    object TvShowsServiceModule {

        @Provides
        @Reusable
        fun provideTvShowsService(@Named(TMDB_RETROFIT) retrofit: Retrofit): TvShowsService {
            return retrofit.create(TvShowsService::class.java)
        }

    }
}