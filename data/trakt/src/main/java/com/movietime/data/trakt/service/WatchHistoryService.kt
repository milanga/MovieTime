package com.movietime.data.trakt.service

import com.movietime.data.trakt.model.MediaRequest
import com.movietime.domain.model.MediaIds
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WatchHistoryService {
    @POST("sync/history")
    suspend fun addToWatched(@Body watchedRequest: MediaRequest)

    @POST("sync/history/remove")
    suspend fun removeFromWatched(@Body watchedRequest: MediaRequest)

    /**
     * @param type "movies" or "shows"
     * */
    @GET("sync/watched/{type}")
    suspend fun getWatched(@Path("type") type: String): List<MediaIds>
}