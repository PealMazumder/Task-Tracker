package com.example.tasktracker.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasktracker.base.BaseViewModel
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.signupModel.RegistrationRequest
import com.example.tasktracker.repository.OtpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(private val repository: OtpRepository) : BaseViewModel() {
    lateinit var sessionId: String
    lateinit var email: String
    lateinit var password: String

    private val _registrationResponse =
        MutableLiveData<TaskTrackerBaseResponse<*>>()
    val verifyOTPResponse: LiveData<TaskTrackerBaseResponse<*>> =
        _registrationResponse

    fun registerUser(requestModel: RegistrationRequest) {
        _showLoader.value = true
        viewModelScope.launch {
            val response = callService { repository.getRegistrationResponse(requestModel) }
            _showLoader.value = false
            response?.let {
                _registrationResponse.value = it
            }
        }
    }

}