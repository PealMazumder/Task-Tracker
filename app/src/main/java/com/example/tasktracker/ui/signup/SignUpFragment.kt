package com.example.tasktracker.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tasktracker.MainActivity
import com.example.tasktracker.UserDetailsValidation.checkPasswordAndConfirmPasswordIsSame
import com.example.tasktracker.UserDetailsValidation.validateEmail
import com.example.tasktracker.UserDetailsValidation.validatePassword
import com.example.tasktracker.base.BaseFragment
import com.example.tasktracker.databinding.FragmentSignUpBinding
import com.example.tasktracker.network.signupModel.RequestClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignUpBinding>() {

    override val mViewModel: SignUpViewModel by viewModels()

    override fun getViewBinding(): FragmentSignUpBinding =
        FragmentSignUpBinding.inflate(layoutInflater)

    private lateinit var sessionId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.btnNext.setOnClickListener { submitForm() }
        mViewBinding.tvLogin.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        mViewModel.otpResponse.observe(this.viewLifecycleOwner) {
            it.message?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
            sessionId = it.data?.sessionId ?: ""
            navigateToNextFragment()
        }
    }

    private fun submitForm() {
        val userEmail = mViewBinding.etEmail.text.toString()
        val isFormValid = formValidation()
        if (isFormValid) {
            val requestClass = RequestClass(userEmail)
            mViewModel.sendOTP(requestClass)
        }
    }

    private fun formValidation(): Boolean {
        val emailText = mViewBinding.etEmail.text.toString()
        val passwordText = mViewBinding.passwordEditText.text.toString()
        val confirmPasswordText = mViewBinding.etConfirmPassword.text.toString()
        val emailFieldHelperText = validateEmail(emailText)
        val passwordFieldHelperText = validatePassword(passwordText)
        val confirmPasswordFieldHelperText =
            checkPasswordAndConfirmPasswordIsSame(passwordText, confirmPasswordText)

        mViewBinding.emailContainer.helperText = emailFieldHelperText
        mViewBinding.passwordContainer.helperText = passwordFieldHelperText
        mViewBinding.confirmPasswordContainer.helperText = confirmPasswordFieldHelperText
        return emailFieldHelperText == null && passwordFieldHelperText == null && confirmPasswordFieldHelperText == null
    }

    private fun navigateToNextFragment() {
        val userEmail = mViewBinding.etEmail.text.toString()
        val userPassword = mViewBinding.passwordEditText.text.toString()
        val action =
            SignUpFragmentDirections.actionSignUpFragmentToOtpFragment(
                userEmail,
                userPassword,
                sessionId
            )
        findNavController().navigate(action)
    }
}