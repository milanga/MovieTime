package com.movietime.data.trakt.di.movie

import com.movietime.data.trakt.datasources.TraktMovieIdsDataSource
import com.movietime.data.trakt.di.network.NetworkModule.Companion.TRAKT_RETROFIT
import com.movietime.data.trakt.mappers.MediaIdsMapper
import com.movietime.data.trakt.service.MovieService
import com.movietime.domain.repository.movie.MovieIdsDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieModule {

    @Binds
    abstract fun bindMovieIdsDataSource(traktMovieIdsDataSource: TraktMovieIdsDataSource): MovieIdsDataSource

    companion object {

        @Provides
        fun providesMovieService(@Named(TRAKT_RETROFIT) retrofit: Retrofit): MovieService {
            return retrofit.create(MovieService::class.java)
        }

        @Provides
        fun provideMovieIdsMapper() = MediaIdsMapper
    }
}