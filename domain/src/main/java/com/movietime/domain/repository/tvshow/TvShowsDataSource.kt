package com.movietime.domain.repository.tvshow

import com.movietime.domain.model.TvShowPreview

interface TvShowsDataSource {
    suspend fun getPopularTvShows(page: Int): List<TvShowPreview>
    suspend fun getTopRatedTvShows(page: Int): List<TvShowPreview>
    suspend fun getOnTheAirTvShows(page: Int): List<TvShowPreview>
    suspend fun getTrendingTvShows(page: Int): List<TvShowPreview>
}