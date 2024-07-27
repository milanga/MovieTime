package com.movietime.data.tmdb.di.tvshow

import com.movietime.data.tmdb.datasource.TmdbTvShowDetailDataSource
import com.movietime.data.tmdb.di.network.NetworkModule.Companion.TMDB_RETROFIT
import com.movietime.data.tmdb.service.TvShowDetailService
import com.movietime.domain.repository.tvshow.TvShowDetailDataSource
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
abstract class TvShowDetailDataSourceModule {

    @Binds
    @Reusable
    abstract fun bindTvShowDetailRemoteDataSource(tmdbTvShowDetailDataSource: TmdbTvShowDetailDataSource): TvShowDetailDataSource

    @InstallIn(SingletonComponent::class)
    @Module
    object TvShowDetailServiceModule {

        @Provides
        @Reusable
        fun provideTvShowDetailService(@Named(TMDB_RETROFIT) retrofit: Retrofit): TvShowDetailService {
            return retrofit.create(TvShowDetailService::class.java)
        }
    }
}