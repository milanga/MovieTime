package com.movietime.domain.interactors.movie

import com.movietime.domain.repository.watchlist.WatchlistRepository
import javax.inject.Inject

class AddTvShowToWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) {
    suspend operator fun invoke(tmdbID: Int) {
        watchlistRepository.addTvShowToWatchlist(tmdbID)
    }
}