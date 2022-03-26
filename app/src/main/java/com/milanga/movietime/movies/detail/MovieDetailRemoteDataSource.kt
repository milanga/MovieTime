package com.milanga.movietime.movies.detail

import com.milanga.movietime.movies.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailRemoteDataSource @Inject constructor(private val movieDetailService: MovieDetailService): MovieDetailDataSource {
    override fun getMovieDetail(movieId: Int): Flow<MovieDetail> = flow {
        emit(movieDetailService.getMovieDetail(movieId))
    }.flowOn(Dispatchers.IO)

    override fun getMovieVideos(movieId: Int): Flow<List<Video>> = flow {
        emit(movieDetailService.movieVideos(movieId).results)
    }.flowOn(Dispatchers.IO)

    override fun getMovieRecommendations(movieId: Int, page: Int): Flow<MoviesResponse> = flow {
        emit(movieDetailService.getMovieRecommendations(movieId, page))
    }.flowOn(Dispatchers.IO)
}