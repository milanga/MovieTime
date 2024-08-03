package com.movietime.data.trakt.datasources

import com.movietime.data.trakt.mappers.MediaIdsMapper
import com.movietime.data.trakt.model.MediaRequest
import com.movietime.data.trakt.model.TraktIds
import com.movietime.data.trakt.model.TraktMediaDetail
import com.movietime.data.trakt.service.WatchlistService
import com.movietime.domain.model.MediaIds
import com.movietime.domain.repository.watchlist.WatchlistDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TraktWatchlistDataSource @Inject constructor(
    private val watchlistService: WatchlistService,
    private val mediaIdsMapper: MediaIdsMapper
): WatchlistDataSource {
    override suspend fun addMovieToWatchlist(tmdbID: Int) {
        val addToWatchListRequest = MediaRequest(
            movieIds = listOf(traktMediaDetail(tmdbID))
        )
        watchlistService.addToWatchlist(addToWatchListRequest)
    }

    override suspend fun removeMovieFromWatchlist(tmdbID: Int) {
        val removeFromWatchListRequest = MediaRequest(
            movieIds = listOf(traktMediaDetail(tmdbID))
        )
        watchlistService.removeFromWatchlist(removeFromWatchListRequest)
    }

    override suspend fun addTvShowToWatchlist(tmdbID: Int) {
        val addToWatchListRequest = MediaRequest(
            tvShowIds = listOf(traktMediaDetail(tmdbID))
        )
        watchlistService.addToWatchlist(addToWatchListRequest)
    }

    override suspend fun removeTvShowFromWatchlist(tmdbID: Int) {
        val removeFromWatchListRequest = MediaRequest(
            tvShowIds = listOf(traktMediaDetail(tmdbID))
        )
        watchlistService.removeFromWatchlist(removeFromWatchListRequest)
    }

    override fun getMovieWatchlistIds(): Flow<List<MediaIds>> {
        return flow {
            emit(watchlistService.getWatchlist("movies", "rank")
                .map { mediaIdsMapper.map(it.movie!!.ids) })
        }
    }

    override fun getTvShowWatchlistIds(): Flow<List<MediaIds>> {
        return flow {
            emit(watchlistService.getWatchlist("shows", "rank")
                .map { mediaIdsMapper.map(it.show!!.ids) })
        }
    }

    override fun setTvShowWatchlistIds(list: List<MediaIds>) {
        // do nothing
    }

    override fun setMovieWatchlistIds(list: List<MediaIds>) {
        // do nothing
    }

    private fun traktMediaDetail(tmdbID: Int): TraktMediaDetail =
        TraktMediaDetail(
            ids = TraktIds(
                tmdb = tmdbID,
            )
        )
}