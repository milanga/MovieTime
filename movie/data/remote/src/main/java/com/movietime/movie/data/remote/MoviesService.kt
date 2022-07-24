package com.movietime.movie.data.remote

import com.movietime.movie.data.remote.model.RemoteMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/top_rated")
    suspend fun topRatedMovies(@Query("page") page: Int): RemoteMoviesResponse

    @GET("movie/upcoming")
    suspend fun upcomingMovies(@Query("page") page: Int): RemoteMoviesResponse

    @GET("movie/popular")
    suspend fun popularMovies(@Query("page") page: Int): RemoteMoviesResponse
}