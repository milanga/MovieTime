package com.movietime.domain.interactors.auth

import com.movietime.domain.oauth.AuthorizationUrlGenerator
import javax.inject.Inject

class GetAuthorizationRedirectUri @Inject constructor(
    private val authorizationUrlGenerator: AuthorizationUrlGenerator
) {
    operator fun invoke(): String {
        return authorizationUrlGenerator.getAuthorizationUrl()
    }
}