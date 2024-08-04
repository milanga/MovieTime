package com.movietime.data.trakt.di.auth

import com.movietime.data.trakt.BuildConfig
import com.movietime.data.trakt.di.network.NetworkModule.Companion.TRAKT_RETROFIT
import com.movietime.data.trakt.di.network.NetworkModule.Companion.TRAKT_RETROFIT_AUTH
import com.movietime.data.trakt.oauth.TraktAuthorizationUrlGenerator
import com.movietime.data.trakt.oauth.TraktTokenDataSource
import com.movietime.data.trakt.service.AuthService
import com.movietime.domain.oauth.AuthorizationUrlGenerator
import com.movietime.domain.repository.oauth.TokenDataSource
import com.movietime.data.trakt.mappers.TokenInfoMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
abstract class AuthModule {
    @Binds
    abstract fun bindAuthorizationUrlGenerator(traktAuthorizationUrlGenerator: TraktAuthorizationUrlGenerator): AuthorizationUrlGenerator

    @Binds
    abstract fun bindTokenDataSource(traktTokenDataSource: TraktTokenDataSource): TokenDataSource

    companion object{
        const val TRAKT_CLIENT_ID_NAME = "TRAKT_CLIENT_ID"
        const val REDIRECT_URI_NAME = "REDIRECT_URI_NAME"
        const val TRAKT_CLIENT_SECRET_NAME = "TRAKT_CLIENT_SECRET"
        private const val REDIRECT_URI = "milanga://movietime.com/auth"

        @Provides
        @Named(TRAKT_CLIENT_ID_NAME)
        fun provideTraktClientId(): String = BuildConfig.TRAKT_CLIENT_ID

        @Provides
        @Named(TRAKT_CLIENT_SECRET_NAME)
        fun provideTraktClientSecret(): String = BuildConfig.TRAKT_CLIENT_SECRET

        @Provides
        @Named(REDIRECT_URI_NAME)
        fun provideRedirectUri(): String = REDIRECT_URI

        @Provides
        @Reusable
        fun provideAuthService(@Named(TRAKT_RETROFIT_AUTH) retrofit: Retrofit): AuthService {
            return retrofit.create(AuthService::class.java)
        }

        @Provides
        @Reusable
        fun provideTokenInfoMapper() = TokenInfoMapper
    }
}