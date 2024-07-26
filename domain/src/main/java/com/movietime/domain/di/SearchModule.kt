package com.movietime.domain.di

import com.movietime.domain.interactors.search.SearchRepository
import com.movietime.domain.repository.search.TmdbSearchRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class SearchModule {

    @Binds
    @Reusable
    abstract fun bindSearchRepository(tmdbSearchRepository: TmdbSearchRepository): SearchRepository
}