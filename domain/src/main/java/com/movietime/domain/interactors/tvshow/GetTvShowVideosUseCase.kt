package com.movietime.domain.interactors.tvshow

import javax.inject.Inject

class GetTvShowVideosUseCase @Inject constructor(
    private val tvShowDetailRepository: TvShowDetailRepository
) {
    operator fun invoke(tmdbTvShowId: Int) = tvShowDetailRepository.getTvShowVideos(tmdbTvShowId)
}