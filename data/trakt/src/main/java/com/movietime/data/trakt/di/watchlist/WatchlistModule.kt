package com.movietime.data.trakt.di.watchlist

import com.movietime.data.trakt.datasources.TraktWatchlistDataSource
import com.movietime.data.trakt.di.network.NetworkModule.Companion.TRAKT_RETROFIT
import com.movietime.data.trakt.service.WatchlistService
import com.movietime.domain.repository.watchlist.WatchlistDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class WatchlistModule {
    @Binds
    abstract fun bindWatchlistDataSource(traktWatchlistDataSource: TraktWatchlistDataSource): WatchlistDataSource

    companion object {
        @Provides
        @Reusable
        fun provideWatchlistService(@Named(TRAKT_RETROFIT) retrofit: Retrofit): WatchlistService {
            return retrofit.create(WatchlistService::class.java)
        }
    }
}