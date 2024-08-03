package com.movietime.data.memory.datasource

import com.movietime.domain.model.MediaIds
import com.movietime.domain.repository.watchlist.WatchlistDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoryWatchlistDataSource @Inject constructor(
): WatchlistDataSource {
    private val tvShowWatchlist = MutableStateFlow(emptyList<MediaIds>())
    private val movieWatchlist = MutableStateFlow(emptyList<MediaIds>())

    override suspend fun addMovieToWatchlist(tmdbID: Int) {
        movieWatchlist.value += MediaIds(tmdb = tmdbID)
    }

    override suspend fun removeMovieFromWatchlist(tmdbID: Int) {
        movieWatchlist.value = movieWatchlist.value.filter { it.tmdb != tmdbID }
    }

    override suspend fun addTvShowToWatchlist(tmdbID: Int) {
        tvShowWatchlist.value += MediaIds(tmdb = tmdbID)
    }

    override suspend fun removeTvShowFromWatchlist(tmdbID: Int) {
        tvShowWatchlist.value = tvShowWatchlist.value.filter { it.tmdb != tmdbID }
    }

    override fun getMovieWatchlistIds(): Flow<List<MediaIds>> {
        return movieWatchlist
    }

    override fun getTvShowWatchlistIds(): Flow<List<MediaIds>> {
        return tvShowWatchlist
    }

    override fun setTvShowWatchlistIds(list: List<MediaIds>) {
        tvShowWatchlist.value = list
    }

    override fun setMovieWatchlistIds(list: List<MediaIds>) {
        movieWatchlist.value = list
    }


}