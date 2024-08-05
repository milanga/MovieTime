package com.movietime.domain.interactors.tvshow

import javax.inject.Inject

class GetTrendingTvShowsUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    val trendingTvShows = tvShowsRepository.trendingTvShows

    suspend fun refresh() = tvShowsRepository.refreshTrendingTvShows()
    suspend fun fetchMore() = tvShowsRepository.fetchMoreTrendingTvShows()
}