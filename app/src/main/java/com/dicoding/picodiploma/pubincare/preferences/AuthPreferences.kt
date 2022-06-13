package com.dicoding.picodiploma.pubincare.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dicoding.picodiploma.pubincare.network.response.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val nameKey = stringPreferencesKey(NAME_KEY)
    private val idKey = stringPreferencesKey(ID_KEY)
    private val tokenKey = stringPreferencesKey(TOKEN_KEY)

    fun getCurrentUser(): Flow<LoginResult> {
        return dataStore.data.map { preferences ->
            LoginResult(
                name = preferences[nameKey] ?: "",
                userId = preferences[idKey] ?: "",
                token = preferences[tokenKey] ?: "",
            )
        }
    }

    suspend fun saveCurrentUser(name: String, userId: String, token: String) {
        dataStore.edit { preferences ->
            preferences[nameKey] = name
            preferences[idKey] = userId
            preferences[tokenKey] = token
        }
    }

    companion object {
        private const val NAME_KEY = "auth_name"
        private const val ID_KEY = "auth_id"
        private const val TOKEN_KEY = "auth_token"

        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}