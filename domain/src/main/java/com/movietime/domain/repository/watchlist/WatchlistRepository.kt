package com.movietime.domain.repository.watchlist

import com.movietime.domain.model.MediaIds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WatchlistRepository @Inject constructor(
    private val watchlistDataSource: WatchlistDataSource
) {
    suspend fun addMovieToWatchlist(tmdbID: Int) {
        watchlistDataSource.addMovieToWatchlist(tmdbID)
    }

    suspend fun addTvShowToWatchlist(tmdbID: Int) {
        watchlistDataSource.addTvShowToWatchlist(tmdbID)
    }

    suspend fun removeMovieFromWatchlist(tmdbID: Int) {
        watchlistDataSource.removeMovieFromWatchlist(tmdbID)
    }

    suspend fun removeTvShowFromWatchlist(tmdbID: Int) {
        watchlistDataSource.removeTvShowFromWatchlist(tmdbID)
    }

    fun getMovieWatchlistIds(): Flow<List<MediaIds>> {
        return flow { emit(watchlistDataSource.getMovieWatchlistIds()) }
    }

    fun getTvShowWatchlistIds(): Flow<List<MediaIds>> {
        return flow { emit(watchlistDataSource.getTvShowWatchlistIds()) }
    }
}