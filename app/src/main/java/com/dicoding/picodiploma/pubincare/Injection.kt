package com.dicoding.picodiploma.pubincare

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.picodiploma.pubincare.network.ApiConfig
import com.dicoding.picodiploma.pubincare.preferences.AuthPreferences
import com.dicoding.picodiploma.pubincare.repository.AuthRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        val authPreference = AuthPreferences.getInstance(context.dataStore)
        return AuthRepository.getInstance(authPreference, apiService)
    }
}