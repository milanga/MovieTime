package com.movietime.domain.model

data class TokenInfo(
    val accessToken: String,
    val tokenType: String,
    val expiresInSeconds: Int,
    val refreshToken: String,
    val scope: String,
    val createdAtInSecondsUtc: Long
)