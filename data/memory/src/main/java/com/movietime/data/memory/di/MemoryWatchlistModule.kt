package com.movietime.data.memory.di

import com.movietime.data.memory.datasource.MemoryWatchlistDataSource
import com.movietime.domain.repository.watchlist.WatchlistDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
interface WatchlistModule {
    @Named("MEMORY_WATCHLIST_DATA_SOURCE")
    @Binds
    fun bindWatchlistDataSource(memoryWatchlistDataSource: MemoryWatchlistDataSource): WatchlistDataSource
}