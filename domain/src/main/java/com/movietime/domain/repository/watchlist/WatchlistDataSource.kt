package com.movietime.domain.repository.watchlist

import com.movietime.domain.model.MediaIds

interface WatchlistDataSource {
    suspend fun addMovieToWatchlist(tmdbID: Int)
    suspend fun addTvShowToWatchlist(tmdbID: Int)
    suspend fun getMovieWatchlistIds(): List<MediaIds>
    suspend fun getTvShowWatchlistIds(): List<MediaIds>
}