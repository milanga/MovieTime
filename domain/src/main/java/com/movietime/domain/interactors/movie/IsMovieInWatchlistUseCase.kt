package com.movietime.domain.interactors.movie

import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsMovieInWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) {
    operator fun invoke(tmdbID: Int): Flow<Boolean> {
        return watchlistRepository.getMovieWatchlistIds().map {
            it.any { it.tmdb == tmdbID }
        }
    }
}