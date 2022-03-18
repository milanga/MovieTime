package com.milanga.movietime.movies

import com.milanga.movietime.movies.detail.MovieDetail
import com.milanga.movietime.movies.detail.VideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/top_rated")
    suspend fun topRatedMovies(@Query("page") page: Int): MoviesResponse

    @GET("movie/upcoming")
    suspend fun upcomingMovies(@Query("page") page: Int): MoviesResponse

    @GET("movie/popular")
    suspend fun popularMovies(@Query("page") page: Int): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieId: Int): MovieDetail

    @GET("movie/{movie_id}/videos")
    suspend fun movieVideos(@Path("movie_id") movieId: Int): VideosResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(@Path("movie_id") movieId: Int, @Query("page") page: Int): MoviesResponse
}