package com.movietime.data.trakt.oauth

import com.movietime.data.trakt.di.auth.AuthModule.Companion.REDIRECT_URI_NAME
import com.movietime.data.trakt.di.auth.AuthModule.Companion.TRAKT_CLIENT_ID_NAME
import com.movietime.data.trakt.di.auth.AuthModule.Companion.TRAKT_CLIENT_SECRET_NAME
import com.movietime.data.trakt.mappers.TokenInfoMapper
import com.movietime.data.trakt.model.TokenRequest
import com.movietime.data.trakt.service.AuthService
import com.movietime.domain.model.TokenInfo
import com.movietime.domain.repository.oauth.TokenDataSource
import javax.inject.Inject
import javax.inject.Named

class TraktTokenDataSource @Inject constructor(
    private val authService: AuthService,
    @Named(TRAKT_CLIENT_ID_NAME)
    private val clientId: String,
    @Named(TRAKT_CLIENT_SECRET_NAME)
    private val clientSecret: String,
    @Named(REDIRECT_URI_NAME)
    private val redirectUri: String,
    private val tokenInfoMapper: TokenInfoMapper
): TokenDataSource {

    override suspend fun getTraktToken(code: String): TokenInfo {
        val tokenRequest = TokenRequest(
            code = code,
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUri = redirectUri
        )
        return authService.getToken(tokenRequest).let {
            tokenInfoMapper.map(it)
        }
    }

    override suspend fun refreshTraktToken(refreshToken: String): TokenInfo {
        val tokenRequest = TokenRequest(
            refreshToken = refreshToken,
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUri = redirectUri
        )
        return authService.getToken(tokenRequest).let {
            tokenInfoMapper.map(it)
        }
    }
}