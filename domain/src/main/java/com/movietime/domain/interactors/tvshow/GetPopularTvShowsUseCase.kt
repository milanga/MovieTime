package com.movietime.domain.interactors.tvshow

import javax.inject.Inject

class GetPopularTvShowsUseCase @Inject constructor(
    private val tvShowsRepository: TvShowsRepository
) {
    val popularTvShows = tvShowsRepository.popularTvShows

    suspend fun refresh() = tvShowsRepository.refreshPopularTvShows()
    suspend fun fetchMore() = tvShowsRepository.fetchMorePopularTvShows()
}