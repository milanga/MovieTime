package com.movietime.domain.interactors.auth

import com.movietime.domain.model.TokenInfo
import com.movietime.domain.repository.oauth.LocalTokenRepository
import javax.inject.Inject

class GetToken @Inject constructor(
    private val tokenRepository: LocalTokenRepository
) {
    suspend operator fun invoke(): TokenInfo? {
        return tokenRepository.getToken()
    }
}