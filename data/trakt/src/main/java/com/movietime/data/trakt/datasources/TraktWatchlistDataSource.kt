package com.movietime.data.trakt.datasources

import com.movietime.data.trakt.mappers.MediaIdsMapper
import com.movietime.data.trakt.model.AddToWatchListRequest
import com.movietime.data.trakt.model.TraktIds
import com.movietime.data.trakt.model.TraktMediaDetail
import com.movietime.data.trakt.service.WatchlistService
import com.movietime.domain.model.MediaIds
import com.movietime.domain.repository.watchlist.WatchlistDataSource
import javax.inject.Inject

class TraktWatchlistDataSource @Inject constructor(
    private val watchlistService: WatchlistService,
    private val mediaIdsMapper: MediaIdsMapper
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

    override suspend fun getMovieWatchlistIds(): List<MediaIds> {
        return watchlistService.getWatchlist("movies", "rank")
            .map { mediaIdsMapper.map(it.movie.ids) }
    }

    override suspend fun getTvShowWatchlistIds(): List<MediaIds> {
        return watchlistService.getWatchlist("shows", "rank")
            .map { mediaIdsMapper.map(it.show.ids) }
    }

    private fun traktMediaDetail(tmdbID: Int): TraktMediaDetail =
        TraktMediaDetail(
            ids = TraktIds(
                tmdb = tmdbID,
            )
        )
}