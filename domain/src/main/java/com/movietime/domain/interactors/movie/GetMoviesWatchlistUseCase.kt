package com.movietime.domain.interactors.movie

import com.movietime.domain.model.MovieDetail
import com.movietime.domain.repository.watchlist.WatchlistRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesWatchlistUseCase @Inject constructor(
    private val watchlistDataSource: WatchlistRepository,
    private val movieDetailRepository: MovieDetailRepository,
) {
    operator fun invoke(): Flow<List<MovieDetail>> {
        return watchlistDataSource.getMovieWatchlistIds().flatMapConcat {
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