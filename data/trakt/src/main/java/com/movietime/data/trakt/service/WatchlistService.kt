package com.movietime.data.trakt.service

import com.movietime.data.trakt.model.MediaRequest
import com.movietime.data.trakt.model.TraktListEntry
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WatchlistService {
    @POST("sync/watchlist")
    suspend fun addToWatchlist(@Body addToWatchListRequest: MediaRequest)

    @POST("sync/watchlist/remove")
    suspend fun removeFromWatchlist(@Body removeFromWatchListRequest: MediaRequest)

    @GET("sync/watchlist/{type}/{sort}")
    suspend fun getWatchlist(
        @Path("type") type: String,
        @Path("sort") sort: String
    ): List<TraktListEntry>
}