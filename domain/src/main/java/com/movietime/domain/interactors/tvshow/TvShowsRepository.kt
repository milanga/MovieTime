package com.movietime.domain.interactors.tvshow

import com.movietime.domain.model.TvShowPreview
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    val popularTvShows: Flow<List<TvShowPreview>>
    val topRatedTvShows: Flow<List<TvShowPreview>>
    val onTheAirTvShows: Flow<List<TvShowPreview>>
    suspend fun refreshPopularTvShows()
    suspend fun fetchMorePopularTvShows()

    suspend fun refreshTopRatedTvShows()
    suspend fun fetchMoreTopRatedTvShows()

    suspend fun refreshOnTheAirTvShows()
    suspend fun fetchMoreOnTheAirTvShows()
}