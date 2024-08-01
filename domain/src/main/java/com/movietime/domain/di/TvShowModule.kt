package com.movietime.domain.di

import com.movietime.domain.interactors.tvshow.TvShowDetailRepository
import com.movietime.domain.interactors.tvshow.TvShowsRepository
import com.movietime.domain.repository.tvshow.RemoteTvShowDetailRepository
import com.movietime.domain.repository.tvshow.RemoteTvShowsRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class TvShowModule {

    @Binds
    @Reusable
    abstract fun bindTvShowDetailRepositoryFactory(remoteTvShowDetailRepository: RemoteTvShowDetailRepository): TvShowDetailRepository

    @Binds
    @Reusable
    abstract fun bindTvShowRepository(remoteTvShowsRepository: RemoteTvShowsRepository): TvShowsRepository
}