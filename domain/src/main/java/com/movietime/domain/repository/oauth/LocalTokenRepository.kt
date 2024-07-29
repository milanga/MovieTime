package com.movietime.domain.repository.oauth

import com.movietime.domain.model.TokenInfo
import javax.inject.Inject

class LocalTokenRepository @Inject constructor(
    private val savableTokenDataSource: SavableTokenDataSource
) {
    suspend fun getToken(): TokenInfo? {
        return savableTokenDataSource.getToken()
    }
}