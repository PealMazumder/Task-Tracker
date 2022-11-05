package com.example.tasktracker.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasktracker.base.BaseViewModel
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.signupModel.RequestClass
import com.example.tasktracker.network.signupModel.ResponseClass
import com.example.tasktracker.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: SignUpRepository) :
    BaseViewModel() {
    private val _otpResponse = MutableLiveData<TaskTrackerBaseResponse<ResponseClass>>()
    val otpResponse: LiveData<TaskTrackerBaseResponse<ResponseClass>> = _otpResponse

    fun sendOTP(requestClass: RequestClass) {
        _showLoader.value = true
        viewModelScope.launch {
            val response = callService { repository.getGenerateOtpResponse(requestClass) }
            _showLoader.value = false
            response?.let {
                _otpResponse.value = it
            }
        }
    }
}