package com.movietime.domain.interactors.tvshow

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetTvShowRecommendationsUseCase @AssistedInject constructor(
    @Assisted private val tvShowDetailRepository: TvShowDetailRepository
) {
    val recommendedTvShows = tvShowDetailRepository.recommendedTvShows

    suspend fun refresh() = tvShowDetailRepository.refreshRecommendations()
    suspend fun fetchMore() = tvShowDetailRepository.fetchMoreRecommendations()
}

@AssistedFactory
interface GetTvShowRecommendationsUseCaseFactory {
    fun create(tvShowDetailRepository: TvShowDetailRepository): GetTvShowRecommendationsUseCase
}