package com.movietime.data.tmdb.service

import com.movietime.data.tmdb.model.TmdbMovieDetail
import com.movietime.data.tmdb.model.TmdbMoviesResponse
import com.movietime.data.tmdb.model.TmdbVideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieId: Int): TmdbMovieDetail

    @GET("movie/{movie_id}/videos")
    suspend fun movieVideos(@Path("movie_id") movieId: Int): TmdbVideosResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(@Path("movie_id") movieId: Int, @Query("page") page: Int): TmdbMoviesResponse
}