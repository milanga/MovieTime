package com.movietime.domain.repository.history

import com.movietime.domain.model.MediaIds

interface WatchedHistoryDataSource {
    suspend fun addMovieToWatched(tmdbID: Int)
    suspend fun addTvShowToWatched(tmdbID: Int)

    suspend fun removeMovieFromWatched(tmdbID: Int)
    suspend fun removeTvShowFromWatched(tmdbID: Int)

    suspend fun getWatchedTvShows(): List<MediaIds>
    suspend fun getWatchedMovies(): List<MediaIds>

}