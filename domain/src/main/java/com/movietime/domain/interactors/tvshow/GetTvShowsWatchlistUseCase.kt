package com.movietime.domain.interactors.tvshow

import com.movietime.domain.interactors.auth.IsUserLoggedInUseCase
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetTvShowsWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    private val tvShowDetailRepository: TvShowDetailRepository,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) {

    operator fun invoke(): Flow<List<TvShowDetail>> {
        return isUserLoggedInUseCase()
            .flatMapLatest { isLoggedIn ->
                if (isLoggedIn) {
                    getTvShowsWatchlist()
                } else {
                    flowOf(emptyList())
                }
            }
    }

    private fun getTvShowsWatchlist(): Flow<List<TvShowDetail>> {
        return watchlistRepository.getTvShowWatchlistIds().flatMapLatest {
            flow {
                emit(coroutineScope {
                    it.map {
                        async { tvShowDetailRepository.getTvShowDetail(it.tmdb).first() }
                    }.awaitAll()
                })
            }
        }
    }
}