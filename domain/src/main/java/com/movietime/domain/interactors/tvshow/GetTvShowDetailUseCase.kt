package com.movietime.domain.interactors.tvshow

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetTvShowDetailUseCase @AssistedInject constructor(
    @Assisted private val tvShowDetailRepository: TvShowDetailRepository
) {
    val tvShowDetail = tvShowDetailRepository.tvShowDetail

    suspend fun fetchTvShowDetail() = tvShowDetailRepository.fetchTvShowDetail()
}

@AssistedFactory
interface GetTvShowDetailUseCaseFactory {
    fun create(tvShowDetailRepository: TvShowDetailRepository): GetTvShowDetailUseCase
}