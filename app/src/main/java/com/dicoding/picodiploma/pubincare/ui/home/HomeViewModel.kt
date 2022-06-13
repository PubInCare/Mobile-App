package com.dicoding.picodiploma.pubincare.ui.home

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.dicoding.picodiploma.pubincare.Injection
import com.dicoding.picodiploma.pubincare.network.response.LoginResult
import com.dicoding.picodiploma.pubincare.repository.AuthRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository,
) :
    ViewModel() {
    val user: LiveData<LoginResult> = authRepository.user.asLiveData()

    fun logout() = viewModelScope.launch {
        authRepository.logout()
    }
}

class HomeViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                Injection.provideAuthRepository(context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}