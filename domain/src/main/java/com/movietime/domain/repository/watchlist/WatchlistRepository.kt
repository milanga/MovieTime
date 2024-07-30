package com.movietime.domain.repository.watchlist

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
}