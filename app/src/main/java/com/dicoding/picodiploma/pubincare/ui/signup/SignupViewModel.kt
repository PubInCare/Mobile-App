package com.dicoding.picodiploma.pubincare.ui.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.pubincare.Injection
import com.dicoding.picodiploma.pubincare.repository.AuthRepository

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun signup(name: String, email: String, password: String) =
        authRepository.signup(name, email, password)
}

class SignupViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(Injection.provideAuthRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}