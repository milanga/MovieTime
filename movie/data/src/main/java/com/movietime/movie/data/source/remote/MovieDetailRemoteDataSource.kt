package com.movietime.movie.data.source.remote

import com.movietime.movie.data.MovieDetailDataSource
import com.movietime.movie.data.source.remote.mappers.MovieDetailMapper
import com.movietime.movie.data.source.remote.mappers.VideoMapper
import com.movietime.movie.domain.detail.MovieDetail
import com.movietime.movie.domain.detail.Video
import com.movietime.movie.data.source.remote.mappers.MoviePreviewMapper
import com.movietime.movie.data.source.remote.model.RemoteMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailRemoteDataSource @Inject constructor(
    private val movieDetailService: MovieDetailService,
    private val moviePreviewMapper: MoviePreviewMapper,
    private val videoMapper: VideoMapper,
    private val movieDetailMapper: MovieDetailMapper,
) : MovieDetailDataSource {
    override fun getMovieDetail(movieId: Int): Flow<MovieDetail> =
        flow {
            emit(movieDetailService.getMovieDetail(movieId))
        }
            .flowOn(Dispatchers.IO)
            .map { remoteMovieDetail ->
                movieDetailMapper.map(remoteMovieDetail)
            }
            .flowOn(Dispatchers.Default)

    override fun getMovieVideos(movieId: Int): Flow<List<Video>> =
        flow {
            emit(movieDetailService.movieVideos(movieId))
        }
            .flowOn(Dispatchers.IO)
            .map { remoteVideoResponse ->
                remoteVideoResponse.results.map { remoteVideo ->
                    videoMapper.map(
                        remoteVideo
                    )
                }
            }
            .flowOn(Dispatchers.Default)

    override fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<com.movietime.movie.domain.MoviePreview>> =
        flow {
            emit(movieDetailService.getMovieRecommendations(movieId, page))
        }
            .flowOn(Dispatchers.IO)
            .map { remoteMoviesResponse: RemoteMoviesResponse ->
                remoteMoviesResponse.movies.map { remoteMoviesPreview ->
                    moviePreviewMapper.map(
                        remoteMoviesPreview
                    )
                }
            }
            .flowOn(Dispatchers.Default)
}