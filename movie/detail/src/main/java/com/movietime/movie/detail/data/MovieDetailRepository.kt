package com.movietime.movie.detail.data

import com.movietime.movie.domain.detail.MovieDetail
import com.movietime.movie.domain.detail.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val moviesRemoteDataSource: MovieDetailDataSource){
    fun getMovieDetail(movieId: Int): Flow<MovieDetail> = moviesRemoteDataSource.getMovieDetail(movieId)
    fun getMovieVideos(movieId: Int): Flow<List<Video>> = moviesRemoteDataSource.getMovieVideos(movieId)
    fun getMovieRecommendations(movieId: Int, page: Int = 1): Flow<List<com.movietime.movie.domain.MoviePreview>> = moviesRemoteDataSource.getMovieRecommendations(movieId, page)
}