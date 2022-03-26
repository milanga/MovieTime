package com.milanga.movietime.movies.detail

import com.milanga.movietime.movies.MoviesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val moviesRemoteDataSource: MovieDetailDataSource){
    fun getMovieDetail(movieId: Int): Flow<MovieDetail> = moviesRemoteDataSource.getMovieDetail(movieId)
    fun getMovieVideos(movieId: Int): Flow<List<Video>> = moviesRemoteDataSource.getMovieVideos(movieId)
    fun getMovieRecommendations(movieId: Int, page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getMovieRecommendations(movieId, page)
}