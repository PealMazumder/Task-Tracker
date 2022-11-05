package com.example.tasktracker.repository

import com.example.tasktracker.network.ApiService
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.signupModel.RegistrationRequest
import retrofit2.Response
import javax.inject.Inject

class OtpRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getRegistrationResponse(request: RegistrationRequest): Response<TaskTrackerBaseResponse<*>> {
        return apiService.requestToRegisterUser(request)
    }
}