package com.milanga.movietime.movies.detail

import com.milanga.movietime.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieId: Int): MovieDetail

    @GET("movie/{movie_id}/videos")
    suspend fun movieVideos(@Path("movie_id") movieId: Int): VideosResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(@Path("movie_id") movieId: Int, @Query("page") page: Int): MoviesResponse
}