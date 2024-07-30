package com.movietime.domain.repository.watchlist

interface WatchlistDataSource {
    suspend fun addMovieToWatchlist(tmdbID: Int)
    suspend fun addTvShowToWatchlist(tmdbID: Int)
}