package com.movietime.domain.interactors.tvshow

import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleTvShowFromWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    private val isTvShowInWatchlistUseCase: IsTvShowInWatchlistUseCase
) {
    suspend operator fun invoke(tmdbID: Int) {
        if (isTvShowInWatchlistUseCase(tmdbID).first()) {
            watchlistRepository.removeTvShowFromWatchlist(tmdbID)
        } else {
            watchlistRepository.addTvShowToWatchlist(tmdbID)
        }
    }
}