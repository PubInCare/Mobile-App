package com.dicoding.picodiploma.pubincare.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.dicoding.picodiploma.pubincare.R
import com.dicoding.picodiploma.pubincare.databinding.FragmentSignupBinding
import com.dicoding.picodiploma.pubincare.network.response.SignupResponse
import com.dicoding.picodiploma.pubincare.Result

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding
    private val viewModel: SignupViewModel by viewModels{
        SignupViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()
        setupAction()
    }

    private fun playAnimation() {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val translation = if(isLandscape) View.TRANSLATION_Y else View.TRANSLATION_X
        val valuesTranslation = if(isLandscape) 50f else 30f

        val titleTextView =
            ObjectAnimator.ofFloat(binding?.titlehomeTextView, View.ALPHA, 1f).setDuration(150)
        val messageTextView =
            ObjectAnimator.ofFloat(binding?.messageTextView, View.ALPHA, 1f).setDuration(150)
        val nameEditText =
            ObjectAnimator.ofFloat(binding?.nameEditText, View.ALPHA, 1f).setDuration(150)
        val emailTextView =
            ObjectAnimator.ofFloat(binding?.emailTextView, View.ALPHA, 1f).setDuration(150)
        val emailEditText =
            ObjectAnimator.ofFloat(binding?.emailEditText, View.ALPHA, 1f).setDuration(150)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding?.passwordEditText, View.ALPHA, 1f).setDuration(150)
        val signupButton =
            ObjectAnimator.ofFloat(binding?.registerButton, View.ALPHA, 1f).setDuration(150)

        AnimatorSet().apply {
            playSequentially(
                titleTextView,
                messageTextView,
                nameEditText,
                emailTextView,
                emailEditText,
                passwordEditText,
                signupButton
            )
            start()
        }
    }

    private fun setupAction() {

        binding?.registerButton?.setOnClickListener {
            val name = binding?.nameEditText?.text.toString()
            val email = binding?.emailEditText?.text.toString()
            val password = binding?.passwordEditText?.text.toString()
            when {
                name.isEmpty() -> {
                    binding?.nameEditText?.error = getString(R.string.input_name)
                }
                email.isEmpty() -> {
                    binding?.emailEditText?.error = getString(R.string.input_email)
                }
                password.isEmpty() -> {
                    binding?.passwordEditText?.error = getString(R.string.input_password)
                }
                else -> {
                    viewModel.signup(name, email, password).observe(viewLifecycleOwner) {
                        signupObserver(it)
                    }
                }
            }
        }
    }

    private fun signupObserver(result: Result<SignupResponse>) {
        when (result) {
            is Result.Loading -> {
                showLoading(true)
            }
            is Result.Success -> {
                showLoading(false)
                view?.findNavController()?.navigate(R.id.action_signupFragment_to_loginFragment)
            }
            is Result.Error -> {
                showLoading(false)
                showMessage(getString(R.string.something_wrong))
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        if (message != "") {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}