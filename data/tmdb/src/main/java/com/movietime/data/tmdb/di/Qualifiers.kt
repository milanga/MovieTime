package com.movietime.data.tmdb.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BackdropBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PosterBaseUrl