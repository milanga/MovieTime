package com.movietime.domain.repository.watchlist

import com.movietime.domain.model.MediaIds
import kotlinx.coroutines.flow.Flow

interface WatchlistDataSource {
    suspend fun addMovieToWatchlist(tmdbID: Int)
    suspend fun removeMovieFromWatchlist(tmdbID: Int)
    suspend fun addTvShowToWatchlist(tmdbID: Int)
    suspend fun removeTvShowFromWatchlist(tmdbID: Int)
    fun getMovieWatchlistIds(): Flow<List<MediaIds>>
    fun getTvShowWatchlistIds(): Flow<List<MediaIds>>
    fun setTvShowWatchlistIds(list: List<MediaIds>)
    fun setMovieWatchlistIds(list: List<MediaIds>)
}