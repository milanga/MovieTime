package com.movietime.data.trakt.service

import com.movietime.data.trakt.model.TraktMediaDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowService {
    /**
     * @param id Trakt ID, Trakt slug, or IMDB ID
     * */
    @GET("shows/{id}")
    suspend fun getTvShowDetail(@Path("id")id: String): TraktMediaDetail
}