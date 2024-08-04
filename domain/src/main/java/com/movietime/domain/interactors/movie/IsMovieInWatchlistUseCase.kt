package com.movietime.domain.interactors.movie

import com.movietime.domain.interactors.auth.IsUserLoggedInUseCase
import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsMovieInWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(tmdbID: Int): Flow<Boolean> {
        return isUserLoggedInUseCase()
            .flatMapLatest { isLoggedIn ->
                if(isLoggedIn) {
                    isMovieInWatchlist(tmdbID)
                } else {
                    flowOf(false)
                }
            }
    }

    private fun isMovieInWatchlist(tmdbID: Int): Flow<Boolean> {
        return watchlistRepository.getMovieWatchlistIds().map {
            it.any { it.tmdb == tmdbID }
        }
    }
}