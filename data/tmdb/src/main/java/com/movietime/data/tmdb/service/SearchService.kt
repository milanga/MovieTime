package com.movietime.data.tmdb.service

import com.movietime.data.tmdb.model.TmdbSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/multi")
    suspend fun search(@Query("query") query: String, @Query("page") page: Int = 1): TmdbSearchResponse
}