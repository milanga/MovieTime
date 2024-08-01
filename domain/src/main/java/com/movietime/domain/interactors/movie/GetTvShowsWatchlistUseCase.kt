package com.movietime.domain.interactors.movie

import com.movietime.domain.interactors.tvshow.TvShowDetailRepository
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTvShowsWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    private val tvShowDetailRepository: TvShowDetailRepository,
) {
    @OptIn(FlowPreview::class)
    operator fun invoke(): Flow<List<TvShowDetail>> {
        return watchlistRepository.getTvShowWatchlistIds().flatMapConcat {
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