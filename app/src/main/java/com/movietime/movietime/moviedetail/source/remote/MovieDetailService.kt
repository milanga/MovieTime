package com.movietime.movietime.moviedetail.source.remote

import com.movietime.movietime.movies.source.remote.model.RemoteMoviesResponse
import com.movietime.movietime.moviedetail.source.remote.model.RemoteMovieDetail
import com.movietime.movietime.moviedetail.source.remote.model.RemoteVideosResponse
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