package com.movietime.domain.interactors.tvshow

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetTvShowVideosUseCase @AssistedInject constructor(
    @Assisted private val tvShowDetailRepository: TvShowDetailRepository
) {
    val tvShowVideos = tvShowDetailRepository.tvShowVideos

    suspend fun fetchTvShowVideos() = tvShowDetailRepository.fetchTvShowVideos()
}

@AssistedFactory
interface GetTvShowVideosUseCaseFactory {
    fun create(tvShowDetailRepository: TvShowDetailRepository): GetTvShowVideosUseCase
}