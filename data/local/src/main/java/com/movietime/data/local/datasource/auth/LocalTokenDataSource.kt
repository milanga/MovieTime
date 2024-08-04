package com.movietime.data.local.datasource.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.movietime.domain.model.TokenInfo
import com.movietime.domain.repository.oauth.SavableTokenDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokenDS")

// Maybe I could move to local_media_ids.proto data store in the future
class LocalTokenDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val gson: Gson
): SavableTokenDataSource {
    private val TOKEN_INFO_KEY = stringPreferencesKey("token_info")

    override suspend fun saveToken(tokenInfo: TokenInfo) {
        context.dataStore.edit { tokenDS ->
            tokenDS.remove(TOKEN_INFO_KEY)
            tokenDS[TOKEN_INFO_KEY] = gson.toJson(tokenInfo)
        }
    }

    override fun getToken(): Flow<TokenInfo?> {
        return context.dataStore.data.map { preferences ->
            if(preferences.contains(TOKEN_INFO_KEY)) {
                gson.fromJson(preferences[TOKEN_INFO_KEY], TokenInfo::class.java)
            } else {
                null
            }
        }
    }
}