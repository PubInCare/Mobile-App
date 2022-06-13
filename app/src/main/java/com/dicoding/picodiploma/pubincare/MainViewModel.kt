package com.dicoding.picodiploma.pubincare

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.pubincare.network.response.LoginResult
import com.dicoding.picodiploma.pubincare.preferences.SettingPreferences
import com.dicoding.picodiploma.pubincare.repository.AuthRepository

class MainViewModel(private val pref: SettingPreferences, authRepository: AuthRepository) : ViewModel() {
    val user: LiveData<LoginResult> = authRepository.user.asLiveData()
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}

class MainViewModelFactory(private val pref: SettingPreferences, private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref, Injection.provideAuthRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}