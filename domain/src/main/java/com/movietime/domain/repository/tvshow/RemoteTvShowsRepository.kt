package com.movietime.domain.repository.tvshow

import com.movietime.domain.interactors.tvshow.TvShowsRepository
import com.movietime.domain.model.TvShowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RemoteTvShowsRepository @Inject constructor(
    private val remoteDataSource: TvShowsDataSource,
): TvShowsRepository {
    private var popularTvShowsPage = 1
    private val _popularTvShows = MutableStateFlow<List<TvShowPreview>>(emptyList())
    override val popularTvShows: Flow<List<TvShowPreview>> = _popularTvShows

    private var topRatedTvShowsPage = 1
    private val _topRatedTvShows = MutableStateFlow<List<TvShowPreview>>(emptyList())
    override val topRatedTvShows: Flow<List<TvShowPreview>> = _topRatedTvShows

    private var onTheAirTvShowsPage = 1
    private val _onTheAirTvShows = MutableStateFlow<List<TvShowPreview>>(emptyList())
    override val onTheAirTvShows: Flow<List<TvShowPreview>> = _onTheAirTvShows

    override suspend fun refreshPopularTvShows() {
        popularTvShowsPage = 1
        _popularTvShows.emit(remoteDataSource.getPopularTvShows(popularTvShowsPage))
    }
    override suspend fun fetchMorePopularTvShows() {
        popularTvShowsPage++
        _popularTvShows.emit(_popularTvShows.value.plus(remoteDataSource.getPopularTvShows(popularTvShowsPage)))
    }

    override suspend fun refreshTopRatedTvShows() {
        topRatedTvShowsPage = 1
        _topRatedTvShows.emit(remoteDataSource.getTopRatedTvShows(topRatedTvShowsPage))
    }
    override suspend fun fetchMoreTopRatedTvShows() {
        topRatedTvShowsPage++
        _topRatedTvShows.emit(_topRatedTvShows.value.plus(remoteDataSource.getTopRatedTvShows(topRatedTvShowsPage)))
    }

    override suspend fun refreshOnTheAirTvShows() {
        onTheAirTvShowsPage = 1
        _onTheAirTvShows.emit(remoteDataSource.getOnTheAirTvShows(onTheAirTvShowsPage))
    }
    override suspend fun fetchMoreOnTheAirTvShows() {
        onTheAirTvShowsPage++
        _onTheAirTvShows.emit(_onTheAirTvShows.value.plus(remoteDataSource.getOnTheAirTvShows(onTheAirTvShowsPage)))
    }
}