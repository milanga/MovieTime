package com.movietime.domain.interactors.movie

import com.movietime.domain.model.MovieDetail
import com.movietime.domain.repository.watchlist.WatchlistRepository
import javax.inject.Inject

class GetMoviesWatchlistUseCase @Inject constructor(
    private val watchlistDataSource: WatchlistRepository,
    private val movieDetailRepository: MovieDetailRepository,
) {
    suspend operator fun invoke(): List<MovieDetail> {
        TODO()
    }
}