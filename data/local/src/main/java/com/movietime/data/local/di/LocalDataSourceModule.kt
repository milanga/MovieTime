package com.movietime.data.local.di

import com.google.gson.Gson
import com.movietime.data.local.datasource.auth.LocalTokenDataSource
import com.movietime.domain.repository.oauth.SavableTokenDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalTokenDataSource(localTokenDataSource: LocalTokenDataSource): SavableTokenDataSource

    companion object {
        @Provides
        fun provideGson(): Gson {
            return Gson()
        }
    }
}