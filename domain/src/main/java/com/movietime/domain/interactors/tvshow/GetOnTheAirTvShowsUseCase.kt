package com.movietime.domain.interactors.tvshow

import javax.inject.Inject

class GetOnTheAirTvShowsUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    val onTheAirTvShows = tvShowsRepository.onTheAirTvShows

    suspend fun refresh() = tvShowsRepository.refreshOnTheAirTvShows()
    suspend fun fetchMore() = tvShowsRepository.fetchMoreOnTheAirTvShows()
}