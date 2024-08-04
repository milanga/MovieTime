package com.movietime.domain.interactors.auth

import com.movietime.domain.repository.oauth.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return tokenRepository.getStoredToken().map { it != null }
    }
}