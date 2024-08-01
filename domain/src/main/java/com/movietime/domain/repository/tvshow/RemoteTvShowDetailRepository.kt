package com.movietime.domain.repository.tvshow

import com.movietime.domain.interactors.tvshow.TvShowDetailRepository
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteTvShowDetailRepository @Inject constructor(
    private val remoteDetailDataSource: TvShowDetailDataSource,
): TvShowDetailRepository {
    override fun getTvShowDetail(tmdbTvShowId: Int): Flow<TvShowDetail> {
        return flow { emit(remoteDetailDataSource.getTvShowDetail(tmdbTvShowId)) }
    }

    override fun getTvShowVideos(tmdbTvShowId: Int): Flow<List<Video>> {
        return flow { emit(remoteDetailDataSource.getTvShowVideos(tmdbTvShowId)) }
    }

    override fun getRecommendations(tmdbTvShowId: Int, page: Int): Flow<List<TvShowPreview>> {
        return flow { emit(remoteDetailDataSource.getTvShowRecommendations(tmdbTvShowId, page)) }
    }
}