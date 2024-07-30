package com.movietime.data.trakt.service

import com.movietime.data.trakt.model.AddToWatchListRequest
import com.movietime.data.trakt.model.TraktListEntry
import com.movietime.data.trakt.model.TraktMediaDetail
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WatchlistService {
    @POST("sync/watchlist")
    suspend fun addToWatchlist(@Body addToWatchListRequest: AddToWatchListRequest)

    @GET("sync/watchlist/{type}/{sort}")
    suspend fun getWatchlist(
        @Path("type") type: String,
        @Path("sort") sort: String
    ): List<TraktListEntry>
}