package com.movietime.data.tmdb.service

import com.movietime.data.tmdb.model.TmdbSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {
    @GET("search/multi")
    suspend fun search(@Path("query") query: String, @Path("query") page: Int = 1): TmdbSearchResponse
}