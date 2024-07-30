package com.movietime.data.trakt.service

import com.movietime.data.trakt.model.AddToWatchListRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WatchlistService {
    @POST("sync/watchlist")
    suspend fun addToWatchlist(@Body addToWatchListRequest: AddToWatchListRequest)

}