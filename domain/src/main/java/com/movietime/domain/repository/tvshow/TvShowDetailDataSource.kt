package com.movietime.domain.repository.tvshow

import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video

interface TvShowDetailDataSource {
    suspend fun getTvShowDetail(tvShowId: Int): TvShowDetail
    suspend fun getTvShowVideos(tvShowId: Int): List<Video>
    suspend fun getTvShowRecommendations(tvShowId: Int, page: Int): List<TvShowPreview>
}