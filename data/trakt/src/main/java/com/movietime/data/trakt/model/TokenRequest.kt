package com.movietime.data.trakt.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenRequest(
    @field:JsonProperty("code")
    val code: String? = null,
    @field:JsonProperty("refresh_token")
    val refreshToken: String? = null,
    @field:JsonProperty("client_id")
    val clientId: String,
    @field:JsonProperty("client_secret")
    val clientSecret: String,
    @field:JsonProperty("redirect_uri")
    val redirectUri: String
) {
    init {
        require(!(code == null && refreshToken == null)) { "Either code or refresh token must be provided" }
    }

    companion object {
        const val AUTH_CODE_GRANT_TYPE = "authorization_code"
        const val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"
    }

    @JsonProperty("grant_type")
    val grantType: String = if (code != null) AUTH_CODE_GRANT_TYPE else REFRESH_TOKEN_GRANT_TYPE
}