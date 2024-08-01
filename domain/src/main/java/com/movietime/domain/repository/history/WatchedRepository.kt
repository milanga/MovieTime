package com.movietime.domain.repository.history

import javax.inject.Inject

class WatchedRepository @Inject constructor(
    private val watchedDataSource: WatchedHistoryDataSource
) {
    suspend fun addMovieToWatched(tmdbID: Int) {
        watchedDataSource.addMovieToWatched(tmdbID)
    }

    suspend fun addTvShowToWatched(tmdbID: Int) {
        watchedDataSource.addTvShowToWatched(tmdbID)
    }

    suspend fun getWatchedTvShows() = watchedDataSource.getWatchedTvShows()

    suspend fun getWatchedMovies() = watchedDataSource.getWatchedMovies()
}