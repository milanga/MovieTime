package com.milanga.movietime.movies

import com.milanga.movietime.movies.detail.MovieDetail
import com.milanga.movietime.movies.detail.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource){
    fun getPopularMovies(page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getPopularMovies(page)
    fun getTopRatedMovies(page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getTopRatedMovies(page)
    fun getUpcomingMovies(page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getUpcomingMovies(page)

    fun getMovieDetail(movieId: Int): Flow<MovieDetail> = moviesRemoteDataSource.getMovieDetail(movieId)
    fun getMovieVideos(movieId: Int): Flow<List<Video>> = moviesRemoteDataSource.getMovieVideos(movieId)
    fun getMovieRecommendations(movieId: Int, page: Int = 1): Flow<MoviesResponse> = moviesRemoteDataSource.getMovieRecommendations(movieId, page)
}