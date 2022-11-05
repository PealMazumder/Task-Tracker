package com.example.tasktracker.network

import com.example.tasktracker.network.getTaskModel.Data
import com.example.tasktracker.network.loginModel.LoginRequest
import com.example.tasktracker.network.loginModel.LoginResponse
import com.example.tasktracker.network.signupModel.RegistrationRequest
import com.example.tasktracker.network.signupModel.RequestClass
import com.example.tasktracker.network.signupModel.ResponseClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/task")
    suspend fun getTask(): Response<TaskTrackerBaseResponse<List<Data>>>

    @POST("/generateOtp")
    suspend fun generateOTP(
        @Body otpRequest: RequestClass
    ): Response<TaskTrackerBaseResponse<ResponseClass>>

    @POST("/register")
    suspend fun requestToRegisterUser(
        @Body registrationRequest: RegistrationRequest
    ): Response<TaskTrackerBaseResponse<*>>

    @POST("/login")
    suspend fun sendUserCredentials(
        @Body loginRequest: LoginRequest
    ): Response<TaskTrackerBaseResponse<LoginResponse>>
}
