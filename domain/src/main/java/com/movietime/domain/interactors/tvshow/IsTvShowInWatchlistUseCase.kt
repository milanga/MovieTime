package com.movietime.domain.interactors.tvshow

import com.movietime.domain.interactors.auth.IsUserLoggedInUseCase
import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsTvShowInWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(tmdbID: Int): Flow<Boolean> {
        return isUserLoggedInUseCase()
            .flatMapLatest { isLoggedIn ->
                if(isLoggedIn) {
                    isTvShowInWatchlist(tmdbID)
                } else {
                    flowOf(false)
                }
            }
    }

    private fun isTvShowInWatchlist(tmdbID: Int): Flow<Boolean> {
        return watchlistRepository.getTvShowWatchlistIds().map {
            it.any { it.tmdb == tmdbID }
        }
    }
}