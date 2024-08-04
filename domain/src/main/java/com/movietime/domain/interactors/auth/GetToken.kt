package com.movietime.domain.interactors.auth

import com.movietime.domain.model.TokenInfo
import com.movietime.domain.repository.oauth.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

private const val FIVE_DAYS_THRESHOLD = 432000

class GetToken @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): TokenInfo? {
        val currentToken = tokenRepository.getStoredToken().firstOrNull()
        return if (currentToken != null && isCloseToExpiration(currentToken)) {
            tokenRepository.refreshToken(currentToken.refreshToken)
        } else {
            currentToken
        }
    }

    private fun isCloseToExpiration(currentToken: TokenInfo) =
        currentToken.createdAtInSecondsUtc + currentToken.expiresInSeconds < System.currentTimeMillis() / 1000 - FIVE_DAYS_THRESHOLD
}