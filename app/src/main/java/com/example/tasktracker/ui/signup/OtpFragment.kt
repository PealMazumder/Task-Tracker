package com.example.tasktracker.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.tasktracker.MainActivity
import com.example.tasktracker.base.BaseFragment
import com.example.tasktracker.databinding.FragmentOtpBinding
import com.example.tasktracker.network.signupModel.RegistrationRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : BaseFragment<OtpViewModel, FragmentOtpBinding>() {

    override val mViewModel: OtpViewModel by viewModels()

    override fun getViewBinding(): FragmentOtpBinding = FragmentOtpBinding.inflate(layoutInflater)

    companion object {
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val SESSION_ID = "sessionId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mViewModel.email = it.getString(EMAIL).toString()
            mViewModel.password = it.getString(PASSWORD).toString()
            mViewModel.sessionId = it.getString(SESSION_ID).toString()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.btnVerifyOtp.setOnClickListener { submitOTP() }
    }

    private fun submitOTP() {
        val otp = mViewBinding.etOtp.text.toString().toInt()
        val requestModel =
            RegistrationRequest(mViewModel.email, otp, mViewModel.password, mViewModel.sessionId)
        mViewModel.registerUser(requestModel)
        mViewModel.verifyOTPResponse.observe(viewLifecycleOwner) {
            it.message?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }

            switchToSignInFragment()
        }
    }

    private fun switchToSignInFragment() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}