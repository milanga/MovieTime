package com.movietime.movietime.moviedetail.data

import com.movietime.movietime.moviedetail.domain.MovieDetail
import com.movietime.movietime.moviedetail.domain.Video
import com.movietime.movietime.movies.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val moviesRemoteDataSource: MovieDetailDataSource){
    fun getMovieDetail(movieId: Int): Flow<MovieDetail> = moviesRemoteDataSource.getMovieDetail(movieId)
    fun getMovieVideos(movieId: Int): Flow<List<Video>> = moviesRemoteDataSource.getMovieVideos(movieId)
    fun getMovieRecommendations(movieId: Int, page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getMovieRecommendations(movieId, page)
}