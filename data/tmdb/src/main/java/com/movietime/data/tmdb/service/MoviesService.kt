package com.movietime.data.tmdb.service

import com.movietime.data.tmdb.model.TmdbMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/top_rated")
    suspend fun topRatedMovies(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbMoviesResponse

    @GET("movie/upcoming")
    suspend fun upcomingMovies(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbMoviesResponse

    @GET("movie/popular")
    suspend fun popularMovies(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbMoviesResponse
}