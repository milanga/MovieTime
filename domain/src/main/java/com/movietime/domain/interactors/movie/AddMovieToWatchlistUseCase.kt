package com.movietime.domain.interactors.movie

import com.movietime.domain.repository.watchlist.WatchlistRepository
import javax.inject.Inject

class AddMovieToWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) {
    suspend operator fun invoke(tmdbID: Int) {
        watchlistRepository.addMovieToWatchlist(tmdbID)
    }
}