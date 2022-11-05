package com.example.tasktracker.ui.signup

import android.os.Bundle
import androidx.activity.viewModels
import com.example.tasktracker.base.BaseActivity
import com.example.tasktracker.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<SignUpMainViewModel, ActivitySignUpBinding>() {
    override val mViewModel: SignUpMainViewModel by viewModels()

    override fun getViewBinding(): ActivitySignUpBinding =
        ActivitySignUpBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
    }
}