package com.milanga.movietime.movies.detail

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@InstallIn(SingletonComponent::class)
@Module
object MovieDetailServiceModule {

    @Provides
    @Reusable
    fun provideMovieDetailService(retrofit: Retrofit): MovieDetailService {
        return retrofit.create(MovieDetailService::class.java)
    }
}