package com.movietime.data.tmdb.datasource

import com.movietime.data.tmdb.service.TvShowDetailService
import com.movietime.data.tmdb.mappers.TvShowDetailMapper
import com.movietime.data.tmdb.mappers.TvShowPreviewMapper
import com.movietime.data.tmdb.mappers.VideoMapper
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video
import com.movietime.domain.repository.tvshow.TvShowDetailDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class TmdbTvShowDetailDataSource @Inject constructor(
    private val tvShowDetailService: TvShowDetailService,
    private val tvShowPreviewMapper: TvShowPreviewMapper,
    private val videoMapper: VideoMapper,
    private val tvShowDetailMapper: TvShowDetailMapper,
    @Named("io_dispatcher") private val coroutineContext: CoroutineContext
) : TvShowDetailDataSource {
    override suspend fun getTvShowDetail(tvShowId: Int): TvShowDetail =
        withContext(coroutineContext) {
            tvShowDetailService.getTvShowDetail(tvShowId).let { remoteTvShowDetail ->
                tvShowDetailMapper.map(remoteTvShowDetail)
            }
        }
    override suspend fun getTvShowVideos(tvShowId: Int): List<Video> =
        withContext(coroutineContext) {
            tvShowDetailService.getTvShowVideos(tvShowId).let { remoteVideosResponse ->
                remoteVideosResponse.results.map { remoteVideo ->
                    videoMapper.map(
                        remoteVideo
                    )
                }
            }
        }

    override suspend fun getTvShowRecommendations(tvShowId: Int, page: Int): List<TvShowPreview> =
        withContext(coroutineContext) {
            tvShowDetailService.getTvShowRecommendations(tvShowId, page).let { remoteTvShowsResponse ->
                remoteTvShowsResponse.results.map { remoteTvShowsPreview ->
                    tvShowPreviewMapper.map(
                        remoteTvShowsPreview
                    )
                }
            }
        }
}