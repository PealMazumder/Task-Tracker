package com.example.tasktracker.repository

import com.example.tasktracker.network.ApiService
import com.example.tasktracker.network.TaskTrackerBaseResponse
import com.example.tasktracker.network.signupModel.RequestClass
import com.example.tasktracker.network.signupModel.ResponseClass
import retrofit2.Response
import javax.inject.Inject

class SignUpRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getGenerateOtpResponse(requestClass: RequestClass): Response<TaskTrackerBaseResponse<ResponseClass>> {
        return apiService.generateOTP(requestClass)
    }
}