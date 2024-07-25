package com.movietime.domain.repository.tvshow

import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video

interface TvShowDetailDataSource {
    suspend fun getTvShowDetail(movieId: Int): TvShowDetail
    suspend fun getTvShowVideos(movieId: Int): List<Video>
    suspend fun getTvShowRecommendations(movieId: Int, page: Int): List<TvShowPreview>
}