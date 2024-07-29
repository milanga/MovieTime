package com.movietime.domain.interactors.auth

import com.movietime.domain.repository.oauth.TokenRepository
import javax.inject.Inject

class StoreToken @Inject constructor(
    private val tokenRepository: TokenRepository
){
    suspend operator fun invoke(code: String) {
        tokenRepository.obtainToken(code)
    }
}