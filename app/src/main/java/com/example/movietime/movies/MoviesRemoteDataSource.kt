package com.example.movietime.movies

import com.example.movietime.movies.detail.MovieDetail
import com.example.movietime.movies.detail.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val moviesService: MoviesService) {
    fun getPopularMovies(page: Int): Flow<MoviesResponse> =  flow {
            emit(moviesService.popularMovies(page))
        }.flowOn(Dispatchers.IO)


    fun getTopRatedMovies(page: Int): Flow<MoviesResponse> = flow {
            emit(moviesService.topRatedMovies(page))
        }.flowOn(Dispatchers.IO)

    fun getUpcomingMovies(page: Int): Flow<MoviesResponse> = flow {
        emit(moviesService.upcomingMovies(page))
    }.flowOn(Dispatchers.IO)

    fun getMovieDetail(movieId: Int): Flow<MovieDetail> = flow {
        emit(moviesService.getMovieDetail(movieId))
    }.flowOn(Dispatchers.IO)

    fun getMovieVideos(movieId: Int): Flow<List<Video>> = flow {
        emit(moviesService.movieVideos(movieId).results)
    }.flowOn(Dispatchers.IO)

    fun getMovieRecommendations(movieId: Int, page: Int): Flow<MoviesResponse> = flow {
        emit(moviesService.getMovieRecommendations(movieId, page))
    }.flowOn(Dispatchers.IO)
}