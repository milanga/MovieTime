package com.movietime.data.trakt.service

import com.movietime.data.trakt.model.TraktMediaDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    /**
     * @param id Trakt ID, Trakt slug, or IMDB ID
     * */
    @GET("movies/{id}")
    suspend fun getMovieDetail(@Path("id") id: String): TraktMediaDetail
}