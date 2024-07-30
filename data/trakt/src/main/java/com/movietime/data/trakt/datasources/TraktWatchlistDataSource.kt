package com.movietime.data.trakt.datasources

import com.movietime.data.trakt.model.AddToWatchListRequest
import com.movietime.data.trakt.model.TraktIds
import com.movietime.data.trakt.model.TraktMediaDetail
import com.movietime.data.trakt.service.WatchlistService
import com.movietime.domain.repository.watchlist.WatchlistDataSource
import javax.inject.Inject

class TraktWatchlistDataSource @Inject constructor(
   private val watchlistService: WatchlistService
): WatchlistDataSource {
    override suspend fun addMovieToWatchlist(tmdbID: Int) {
        val addToWatchListRequest = AddToWatchListRequest(
            movieIds = listOf(traktMediaDetail(tmdbID))
        )
        watchlistService.addToWatchlist(addToWatchListRequest)
    }

    override suspend fun addTvShowToWatchlist(tmdbID: Int) {
        val addToWatchListRequest = AddToWatchListRequest(
            tvShowIds = listOf(traktMediaDetail(tmdbID))
        )
        watchlistService.addToWatchlist(addToWatchListRequest)
    }

    private fun traktMediaDetail(tmdbID: Int): TraktMediaDetail =
        TraktMediaDetail(
            ids = TraktIds(
                tmdb = tmdbID,
            )
        )
}