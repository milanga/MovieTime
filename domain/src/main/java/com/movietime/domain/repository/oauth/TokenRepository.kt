package com.movietime.domain.repository.oauth

import com.movietime.domain.model.TokenInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val remoteTokenDataSource: TokenDataSource,
    private val savableTokenDataSource: SavableTokenDataSource
) {
    suspend fun obtainToken(code: String): TokenInfo {
        return remoteTokenDataSource.getTraktToken(code).also {
            savableTokenDataSource.saveToken(it)
        }
    }

    suspend fun refreshToken(refreshToken: String): TokenInfo {
        return remoteTokenDataSource.refreshTraktToken(refreshToken).also {
            savableTokenDataSource.saveToken(it)
        }
    }

    suspend fun getStoredToken(): TokenInfo? {
        return savableTokenDataSource.getToken()
    }
}