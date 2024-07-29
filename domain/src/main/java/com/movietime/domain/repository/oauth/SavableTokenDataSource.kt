package com.movietime.domain.repository.oauth

import com.movietime.domain.model.TokenInfo

interface SavableTokenDataSource {
    suspend fun saveToken(tokenInfo: TokenInfo)
    suspend fun getToken(): TokenInfo?
}