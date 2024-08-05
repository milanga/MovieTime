package com.movietime.data.tmdb.datasource

import com.movietime.data.tmdb.service.TvShowsService
import com.movietime.data.tmdb.mappers.TvShowPreviewMapper
import com.movietime.data.tmdb.model.TmdbTvShowsResponse
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.repository.tvshow.TvShowsDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class TmdbTvShowsDataSource @Inject constructor(
    private val tvShowsService: TvShowsService,
    private val mapper: TvShowPreviewMapper,
    @Named("io_dispatcher") private val coroutineContext: CoroutineContext
) : TvShowsDataSource {
    override suspend fun getPopularTvShows(page: Int): List<TvShowPreview> = withContext(coroutineContext) {
        getListOfTvShows(tvShowsService.popularTvShows(page))
    }

    override suspend fun getTopRatedTvShows(page: Int): List<TvShowPreview> = withContext(coroutineContext) {
        getListOfTvShows(tvShowsService.topRatedTvShows(page))
    }


    override suspend fun getOnTheAirTvShows(page: Int): List<TvShowPreview> = withContext(coroutineContext) {
        getListOfTvShows(tvShowsService.onTheAirTvShows(page))
    }

    override suspend fun getTrendingTvShows(page: Int): List<TvShowPreview> = withContext(coroutineContext) {
        getListOfTvShows(tvShowsService.trendingTvShows(page = page))
    }

    private fun getListOfTvShows(tmdbTvShowsResponse: TmdbTvShowsResponse): List<TvShowPreview> =
        tmdbTvShowsResponse.results.map { remoteTvShowPreview ->
            mapper.map(
                remoteTvShowPreview
            )
        }

}