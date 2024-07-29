package com.movietime.domain.oauth

interface AuthorizationUrlGenerator {
    fun getAuthorizationUrl(): String
}