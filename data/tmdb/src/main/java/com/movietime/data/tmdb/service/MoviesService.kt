package com.movietime.data.tmdb.service

import com.movietime.data.tmdb.model.TmdbMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/top_rated")
    suspend fun topRatedMovies(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbMoviesResponse

    @GET("movie/upcoming")
    suspend fun upcomingMovies(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbMoviesResponse

    @GET("movie/popular")
    suspend fun popularMovies(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbMoviesResponse

    @GET("trending/movie/{time_window}")
    suspend fun trendingMovies(
        @Path("time_window") timeWindow: String = "week",
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): TmdbMoviesResponse
}