package com.movietime.data.tmdb.service

import com.movietime.data.tmdb.model.TmdbTvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowsService {
    @GET("tv/top_rated")
    suspend fun topRatedTvShows(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbTvShowsResponse

    @GET("tv/on_the_air")
    suspend fun onTheAirTvShows(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbTvShowsResponse

    @GET("tv/popular")
    suspend fun popularTvShows(@Query("page") page: Int, @Query("region") region: String = "US"): TmdbTvShowsResponse
}