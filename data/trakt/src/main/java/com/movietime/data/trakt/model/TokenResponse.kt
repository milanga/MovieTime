package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("expires_in")
    val expiresInSeconds: Int,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("scope")
    val scope: String,
    @JsonProperty("created_at")
    val createdAtSecondsUtc: Long
)