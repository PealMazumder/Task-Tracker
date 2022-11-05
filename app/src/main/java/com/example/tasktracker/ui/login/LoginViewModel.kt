package com.example.tasktracker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tasktracker.base.BaseViewModel
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.loginModel.LoginRequest
import com.example.tasktracker.network.loginModel.LoginResponse
import com.example.tasktracker.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    BaseViewModel() {
    val responseMessage: LiveData<String> = _showMessage
    private val _apiResponse = MutableLiveData<TaskTrackerBaseResponse<LoginResponse>>()
    val apiResponse: LiveData<TaskTrackerBaseResponse<LoginResponse>> = _apiResponse

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun sendLoginRequest(userDetails: LoginRequest) {
        _showLoader.value = true
        viewModelScope.launch {
            val response = callService { repository.getLoginResponse(userDetails) }
            _showLoader.value = false
            response?.let {
                _apiResponse.value = it
                _token.value = it?.data?.token
            }
        }
    }
}