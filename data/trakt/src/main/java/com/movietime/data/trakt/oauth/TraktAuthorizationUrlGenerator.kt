package com.movietime.data.trakt.oauth

import com.movietime.data.trakt.di.auth.AuthModule.Companion.REDIRECT_URI_NAME
import com.movietime.data.trakt.di.auth.AuthModule.Companion.TRAKT_CLIENT_ID_NAME
import com.movietime.domain.oauth.AuthorizationUrlGenerator
import javax.inject.Inject
import javax.inject.Named

class TraktAuthorizationUrlGenerator @Inject constructor(
    @Named(TRAKT_CLIENT_ID_NAME)
    private val traktClientId: String,
    @Named(REDIRECT_URI_NAME)
    private val redirectUri: String
): AuthorizationUrlGenerator {
    override fun getAuthorizationUrl(): String {
        return "https://api.trakt.tv/oauth/authorize?response_type=code&client_id=$traktClientId&redirect_uri=$redirectUri"
    }
}