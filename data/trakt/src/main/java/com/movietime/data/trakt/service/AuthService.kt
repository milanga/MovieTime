package com.movietime.data.trakt.service

import com.movietime.data.trakt.model.TokenRequest
import com.movietime.data.trakt.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("oauth/token")
    suspend fun getToken(@Body tokenRequest: TokenRequest): TokenResponse
}