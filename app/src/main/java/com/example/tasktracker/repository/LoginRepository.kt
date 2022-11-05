package com.example.tasktracker.repository

import com.example.tasktracker.network.ApiService
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.loginModel.LoginRequest
import com.example.tasktracker.network.loginModel.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getLoginResponse(userDetails: LoginRequest): Response<TaskTrackerBaseResponse<LoginResponse>> {
        return apiService.sendUserCredentials(userDetails)
    }
}