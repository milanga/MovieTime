package com.movietime.data.tmdb.di.baseurls

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object BaseUrlModule {
    @Provides
    @Reusable
    @BackdropBaseUrl
    fun provideBackdropImageBaseUrl(): String = "http://image.tmdb.org/t/p/original"

    @Provides
    @Reusable
    @PosterBaseUrl
    fun providePosterImageBaseUrl(): String = "http://image.tmdb.org/t/p/w500"

}