package com.movietime.domain.repository.tvshow

import com.movietime.domain.interactors.tvshow.TvShowDetailRepository
import com.movietime.domain.interactors.tvshow.TvShowDetailRepositoryFactory
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class RemoteTvShowDetailRepository @AssistedInject constructor(
    private val remoteDetailDataSource: TvShowDetailDataSource,
    @Assisted private val tvShowId: Int
): TvShowDetailRepository {
    private var recommendationsPage = 1
    private val _recommendedTvShows = MutableStateFlow<List<TvShowPreview>>(emptyList())
    override val recommendedTvShows: Flow<List<TvShowPreview>> = _recommendedTvShows

    private val _tvShowDetail = MutableSharedFlow<TvShowDetail>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val tvShowDetail: Flow<TvShowDetail> = _tvShowDetail

    private val _tvShowVideos = MutableSharedFlow<List<Video>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val tvShowVideos: Flow<List<Video>> = _tvShowVideos

    override suspend fun fetchTvShowDetail() {
        _tvShowDetail.emit(remoteDetailDataSource.getTvShowDetail(tvShowId))
    }
    override suspend fun fetchTvShowVideos() {
        _tvShowVideos.emit(remoteDetailDataSource.getTvShowVideos(tvShowId))
    }

    override suspend fun refreshRecommendations() {
        recommendationsPage = 1
        _recommendedTvShows.emit(remoteDetailDataSource.getTvShowRecommendations(tvShowId, recommendationsPage))
    }
    override suspend fun fetchMoreRecommendations() {
        recommendationsPage++
        _recommendedTvShows.emit(_recommendedTvShows.value.plus(remoteDetailDataSource.getTvShowRecommendations(tvShowId, recommendationsPage)))
    }
}

@AssistedFactory
interface RemoteTvShowDetailRepositoryFactory:
    TvShowDetailRepositoryFactory {
    override fun create(tvShowId: Int): RemoteTvShowDetailRepository
}