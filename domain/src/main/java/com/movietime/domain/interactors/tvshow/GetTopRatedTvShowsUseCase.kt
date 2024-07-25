package com.movietime.domain.interactors.tvshow

import javax.inject.Inject

class GetTopRatedTvShowsUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    val topRatedTvShows = tvShowsRepository.topRatedTvShows

    suspend fun refresh() = tvShowsRepository.refreshTopRatedTvShows()
    suspend fun fetchMore() = tvShowsRepository.fetchMoreTopRatedTvShows()
}