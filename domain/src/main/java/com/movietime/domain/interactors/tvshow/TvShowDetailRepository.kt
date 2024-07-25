package com.movietime.domain.interactors.tvshow

import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface TvShowDetailRepository {
    val tvShowDetail: Flow<TvShowDetail>
    val tvShowVideos: Flow<List<Video>>
    val recommendedTvShows: Flow<List<TvShowPreview>>

    suspend fun fetchTvShowDetail()
    suspend fun fetchTvShowVideos()

    suspend fun refreshRecommendations()
    suspend fun fetchMoreRecommendations()
}

interface TvShowDetailRepositoryFactory{
    fun create(tvShowId: Int): TvShowDetailRepository
}