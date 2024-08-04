package com.movietime.domain.interactors.movie

import com.movietime.domain.interactors.auth.IsUserLoggedInUseCase
import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleMovieFromWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    private val isMovieInWatchlistUseCase: IsMovieInWatchlistUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) {
    suspend operator fun invoke(tmdbID: Int) {
        if (isUserLoggedInUseCase().first()) {
            if (isMovieInWatchlistUseCase(tmdbID).first()) {
                watchlistRepository.removeMovieFromWatchlist(tmdbID)
            } else {
                watchlistRepository.addMovieToWatchlist(tmdbID)
            }
        }
    }
}