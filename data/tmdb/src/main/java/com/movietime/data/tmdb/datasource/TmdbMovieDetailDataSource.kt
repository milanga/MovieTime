package com.movietime.data.tmdb.datasource

import com.movietime.data.tmdb.service.MovieDetailService
import com.movietime.data.tmdb.mappers.MovieDetailMapper
import com.movietime.data.tmdb.mappers.MoviePreviewMapper
import com.movietime.data.tmdb.mappers.VideoMapper
import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video
import com.movietime.domain.repository.movie.MovieDetailDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class TmdbMovieDetailDataSource @Inject constructor(
    private val movieDetailService: MovieDetailService,
    private val moviePreviewMapper: MoviePreviewMapper,
    private val videoMapper: VideoMapper,
    private val movieDetailMapper: MovieDetailMapper,
    @Named("io_dispatcher") private val coroutineContext: CoroutineContext
) : MovieDetailDataSource {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail =
        withContext(coroutineContext) {
            movieDetailService.getMovieDetail(movieId).let { remoteMovieDetail ->
                movieDetailMapper.map(remoteMovieDetail)
            }
        }
    override suspend fun getMovieVideos(movieId: Int): List<Video> =
        withContext(coroutineContext) {
            movieDetailService.movieVideos(movieId).let { remoteVideosResponse ->
                remoteVideosResponse.results.map { remoteVideo ->
                    videoMapper.map(
                        remoteVideo
                    )
                }
            }
        }

    override suspend fun getMovieRecommendations(movieId: Int, page: Int): List<MoviePreview> =
        withContext(coroutineContext) {
            movieDetailService.getMovieRecommendations(movieId, page).let { remoteMoviesResponse ->
                remoteMoviesResponse.movies.map { remoteMoviesPreview ->
                    moviePreviewMapper.map(
                        remoteMoviesPreview
                    )
                }
            }
        }
}