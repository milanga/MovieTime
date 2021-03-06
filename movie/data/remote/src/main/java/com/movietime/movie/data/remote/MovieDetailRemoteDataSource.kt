package com.movietime.movie.data.remote


import com.movietime.movie.data.remote.mappers.MovieDetailMapper
import com.movietime.movie.data.remote.mappers.MoviePreviewMapper
import com.movietime.movie.data.remote.mappers.VideoMapper
import com.movietime.movie.data.remote.model.RemoteMoviesResponse
import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.model.Video
import com.movietime.movie.domain.repository.MovieDetailDataSource
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

    override fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<MoviePreview>> =
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