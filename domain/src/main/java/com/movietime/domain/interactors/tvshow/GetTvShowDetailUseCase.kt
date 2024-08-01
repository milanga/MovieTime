package com.movietime.domain.interactors.tvshow

import javax.inject.Inject

class GetTvShowDetailUseCase @Inject constructor(
    private val tvShowDetailRepository: TvShowDetailRepository
) {
    operator fun invoke(tmdbTvShowId: Int) = tvShowDetailRepository.getTvShowDetail(tmdbTvShowId)
}