package com.movietime.data.trakt.mappers

import com.movietime.data.trakt.model.TokenResponse
import com.movietime.domain.model.TokenInfo

object TokenInfoMapper {
    fun map(tokenResponse: TokenResponse): TokenInfo =
        TokenInfo(
            tokenResponse.accessToken,
            tokenResponse.tokenType,
            tokenResponse.expiresInSeconds,
            tokenResponse.refreshToken,
            tokenResponse.scope,
            tokenResponse.createdAtSecondsUtc
        )
}