package com.example.tasktracker

import android.util.Patterns

object UserDetailsValidation {

    fun validateEmail(emailText: String): String? {
        return if (emailText.isEmpty()) {
            "Field can't be empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            "Invalid Email Address"
        } else {
            null
        }
    }

    fun validatePassword(passwordText: String): String? {

        return if (passwordText.isEmpty()) {
            "Field can't be empty"
        } else if (passwordText.length < 8) {
            "Minimum 8 Character Password"
        } else if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            //"Must Contain 1 Upper-case Character"
            null
        } else if (!passwordText.matches(".*[a-z].*".toRegex())) {
            "Must Contain 1 Lower-case Character"
        } else if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            //"Must Contain 1 Special Character (@#\$%^&+=)"
            null
        } else {
            null
        }
    }

    fun checkPasswordAndConfirmPasswordIsSame(
        passwordText: String,
        confirmPasswordText: String
    ): String? {
        return if (passwordText != confirmPasswordText) {
            "Password Mismatch"
        } else {
            null
        }
    }

}