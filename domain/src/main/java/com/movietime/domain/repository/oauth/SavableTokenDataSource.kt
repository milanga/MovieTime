package com.movietime.domain.repository.oauth

import com.movietime.domain.model.TokenInfo
import kotlinx.coroutines.flow.Flow

interface SavableTokenDataSource {
    suspend fun saveToken(tokenInfo: TokenInfo)
    fun getToken(): Flow<TokenInfo?>
}