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
}