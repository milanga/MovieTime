package com.movietime.movie.data.remote

import com.movietime.movie.data.remote.model.RemoteMovieDetail
import com.movietime.movie.data.remote.model.RemoteMoviesResponse
import com.movietime.movie.data.remote.model.RemoteVideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieId: Int): RemoteMovieDetail

    @GET("movie/{movie_id}/videos")
    suspend fun movieVideos(@Path("movie_id") movieId: Int): RemoteVideosResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(@Path("movie_id") movieId: Int, @Query("page") page: Int): RemoteMoviesResponse
}