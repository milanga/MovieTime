package com.movietime.domain.repository.oauth

import com.movietime.domain.model.TokenInfo

interface TokenDataSource {
    suspend fun getTraktToken(code: String): TokenInfo
    suspend fun refreshTraktToken(refreshToken: String): TokenInfo
}