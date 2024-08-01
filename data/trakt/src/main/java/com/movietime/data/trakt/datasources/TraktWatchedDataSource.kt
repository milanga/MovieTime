package com.movietime.data.trakt.datasources

import com.movietime.data.trakt.model.MediaRequest
import com.movietime.data.trakt.model.TraktIds
import com.movietime.data.trakt.model.TraktMediaDetail
import com.movietime.data.trakt.service.WatchHistoryService
import com.movietime.domain.model.MediaIds
import com.movietime.domain.repository.history.WatchedHistoryDataSource
import javax.inject.Inject

class TraktWatchedDataSource @Inject constructor(
    private val watchHistoryService: WatchHistoryService
): WatchedHistoryDataSource {
    override suspend fun addMovieToWatched(tmdbID: Int) {
        val mediaRequest = MediaRequest(
            movieIds = listOf(traktMediaDetail(tmdbID))
        )
        return watchHistoryService.addToWatched(mediaRequest)
    }

    override suspend fun addTvShowToWatched(tmdbID: Int) {
        val mediaRequest = MediaRequest(
            tvShowIds = listOf(traktMediaDetail(tmdbID))
        )
        return watchHistoryService.addToWatched(mediaRequest)
    }

    override suspend fun removeMovieFromWatched(tmdbID: Int) {
        val mediaRequest = MediaRequest(
            movieIds = listOf(traktMediaDetail(tmdbID))
        )
        return watchHistoryService.removeFromWatched(mediaRequest)
    }

    override suspend fun removeTvShowFromWatched(tmdbID: Int) {
        val mediaRequest = MediaRequest(
            tvShowIds = listOf(traktMediaDetail(tmdbID))
        )
        return watchHistoryService.removeFromWatched(mediaRequest)
    }

    override suspend fun getWatchedTvShows(): List<MediaIds> {
        return watchHistoryService.getWatched("shows")
    }

    override suspend fun getWatchedMovies(): List<MediaIds> {
        return watchHistoryService.getWatched("movies")
    }

    private fun traktMediaDetail(tmdbID: Int): TraktMediaDetail =
        TraktMediaDetail(
            ids = TraktIds(
                tmdb = tmdbID,
            )
        )

}