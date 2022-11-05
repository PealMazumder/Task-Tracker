package com.example.tasktracker.network.signupModel

data class RegistrationRequest(
    var email: String,
    var otp: Int,
    var password: String,
    var sessionId: String
)