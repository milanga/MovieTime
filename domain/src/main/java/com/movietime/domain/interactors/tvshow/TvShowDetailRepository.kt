package com.movietime.domain.interactors.tvshow

import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface TvShowDetailRepository {
    fun getTvShowDetail(tmdbTvShowId: Int): Flow<TvShowDetail>
    fun getTvShowVideos(tmdbTvShowId: Int): Flow<List<Video>>
    fun getRecommendations(tmdbTvShowId: Int, page: Int): Flow<List<TvShowPreview>>
}