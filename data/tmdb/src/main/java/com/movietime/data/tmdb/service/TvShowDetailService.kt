package com.movietime.data.tmdb.service

import com.movietime.data.tmdb.model.TmdbTvShowDetail
import com.movietime.data.tmdb.model.TmdbTvShowsResponse
import com.movietime.data.tmdb.model.TmdbVideosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowDetailService {
    @GET("tv/{series_id}")
    suspend fun getTvShowDetail(@Path("series_id")seriesId: Int): TmdbTvShowDetail

    @GET("tv/{series_id}/videos")
    suspend fun getTvShowVideos(@Path("series_id") seriesId: Int): TmdbVideosResponse

    @GET("tv/{series_id}/recommendations")
    suspend fun getTvShowRecommendations(@Path("series_id") seriesId: Int, @Query("page") page: Int): TmdbTvShowsResponse
}