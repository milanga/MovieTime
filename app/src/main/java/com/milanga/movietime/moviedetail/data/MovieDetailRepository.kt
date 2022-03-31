package com.milanga.movietime.moviedetail.data

import com.milanga.movietime.moviedetail.domain.MovieDetail
import com.milanga.movietime.moviedetail.domain.Video
import com.milanga.movietime.movies.domain.MoviePreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val moviesRemoteDataSource: MovieDetailDataSource){
    fun getMovieDetail(movieId: Int): Flow<MovieDetail> = moviesRemoteDataSource.getMovieDetail(movieId)
    fun getMovieVideos(movieId: Int): Flow<List<Video>> = moviesRemoteDataSource.getMovieVideos(movieId)
    fun getMovieRecommendations(movieId: Int, page: Int = 1): Flow<List<MoviePreview>> = moviesRemoteDataSource.getMovieRecommendations(movieId, page)
}