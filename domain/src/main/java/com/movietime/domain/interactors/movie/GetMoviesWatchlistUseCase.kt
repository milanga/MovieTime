package com.movietime.domain.interactors.movie

import com.movietime.domain.interactors.auth.IsUserLoggedInUseCase
import com.movietime.domain.model.MovieDetail
import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetMoviesWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    private val movieDetailRepository: MovieDetailRepository,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) {
    operator fun invoke(): Flow<List<MovieDetail>> {
        return isUserLoggedInUseCase()
            .flatMapLatest { isLoggedIn ->
                if (isLoggedIn) {
                    getWatchlistMovies()
                } else {
                    flowOf(emptyList())
                }
            }
    }

    private fun getWatchlistMovies(): Flow<List<MovieDetail>> {
        return watchlistRepository.getMovieWatchlistIds().flatMapLatest {
            flow {
                emit(coroutineScope {
                    it.map {
                        async { movieDetailRepository.getMovieDetail(it.tmdb).first() }
                    }.awaitAll()
                })
            }
        }
    }
}