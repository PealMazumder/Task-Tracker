package com.example.tasktracker

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.tasktracker.base.BaseActivity
import com.example.tasktracker.databinding.ActivityMainBinding
import com.example.tasktracker.network.loginModel.LoginRequest
import com.example.tasktracker.ui.home.HomeActivity
import com.example.tasktracker.ui.login.LoginViewModel
import com.example.tasktracker.ui.signup.SignUpActivity
import com.example.tasktracker.utils.Constants
import com.example.tasktracker.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<LoginViewModel, ActivityMainBinding>() {

    override val mViewModel: LoginViewModel by viewModels()
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        loginTextGradientColor()

        mViewBinding.btnLogin.setOnClickListener { submitUserDetails() }

        switchToSignUpActivity()
        mViewModel.responseMessage.observe(this) {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        mViewBinding.etEmail.setText(SharedPreferenceManager(application).getValue("USER_EMAIL"))
    }

    override fun onPause() {
        super.onPause()
        mViewModel.token.observe(this) {
            if (it != null) {
                SharedPreferenceManager(application).saveValue("USER_TOKEN", it)
            }
        }
    }

    private fun submitUserDetails() {
        mViewBinding.cbxRememberMe.isChecked.let {
            SharedPreferenceManager(application).saveValue(
                Constants.USER_EMAIL,
                mViewBinding.etEmail.text.toString()
            )
        }
        val isUserDetailsValid = validateUserDetails()
        if (isUserDetailsValid) {
            setUserDetails()
        }
    }

    private fun validateUserDetails(): Boolean {
        val userEmail = mViewBinding.etEmail.text.toString()
        val userPassword = mViewBinding.etPassword.text.toString()
        val emailFieldHelperText = UserDetailsValidation.validateEmail(userEmail)
        val passwordFieldHelperText = UserDetailsValidation.validatePassword(userPassword)
        mViewBinding.emailContainer.helperText = emailFieldHelperText
        mViewBinding.passwordContainer.helperText = passwordFieldHelperText
        return emailFieldHelperText == null && passwordFieldHelperText == null
    }

    private fun setUserDetails() {
        val userEmail = mViewBinding.etEmail.text.toString()
        val userPassword = mViewBinding.etPassword.text.toString()
        val userDetails = LoginRequest(userEmail, userPassword)

        mViewModel.sendLoginRequest(userDetails)

        mViewModel.apiResponse.observe(this) {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            switchToHomeActivity()
        }
    }

    private fun switchToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


    private fun switchToSignUpActivity() {
        mViewBinding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginTextGradientColor() {

        val paint = mViewBinding.tvLogin.paint
        val width = paint.measureText(mViewBinding.tvLogin.text.toString())
        val textShader: Shader = LinearGradient(
            0f, 0f, width, mViewBinding.tvLogin.textSize, intArrayOf(
                Color.parseColor("#FFED1556"),
                Color.parseColor("#FFB24198"),
                Color.parseColor("#FF2EB1E6")
            ), null, Shader.TileMode.REPEAT
        )

        mViewBinding.tvLogin.paint.shader = textShader
    }
}