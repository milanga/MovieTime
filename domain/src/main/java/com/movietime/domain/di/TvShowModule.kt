package com.movietime.domain.di

import com.movietime.domain.interactors.tvshow.TvShowDetailRepositoryFactory
import com.movietime.domain.interactors.tvshow.TvShowsRepository
import com.movietime.domain.repository.tvshow.RemoteTvShowDetailRepositoryFactory
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
    abstract fun bindTvShowDetailRepositoryFactory(remoteTvShowDetailRepositoryFactory: RemoteTvShowDetailRepositoryFactory): TvShowDetailRepositoryFactory

    @Binds
    @Reusable
    abstract fun bindTvShowRepository(remoteTvShowsRepository: RemoteTvShowsRepository): TvShowsRepository
}