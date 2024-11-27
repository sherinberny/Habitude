package com.example.habitude.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.habitude.ui.repositories.SignUpException
import com.example.habitude.ui.repositories.UserRepository

class SignUpScreenState {
    var email by mutableStateOf("")
    var emailConfirmation by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConfirmation by mutableStateOf("")
    var emailError by mutableStateOf(false)
    var emailConfirmationError by mutableStateOf(false)
    var passwordError by mutableStateOf(false)
    var passwordConfirmationError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var name by mutableStateOf("")
    var age by mutableStateOf("")
    var gender by mutableStateOf("")
    var height by mutableStateOf("")
    var weight by mutableStateOf("")
}

class SignUpViewModel(application: Application): AndroidViewModel(application) {
    val uiState = SignUpScreenState()

    suspend fun signUp() {
        // clear existing errors
        uiState.emailError = false
        uiState.emailConfirmationError = false
        uiState.passwordError = false
        uiState.passwordConfirmationError = false
        uiState.errorMessage = ""

        // Validate the existing fields
        if (!uiState.email.contains("@")) {
            uiState.emailError = true
            uiState.errorMessage = "Email is invalid."
            return
        }
        if (uiState.email != uiState.emailConfirmation) {
            uiState.emailConfirmationError = true
            uiState.errorMessage = "Email confirmation does not match."
            return
        }

        if (uiState.password.length < 8) {
            uiState.passwordError = true
            uiState.errorMessage = "Password needs to be at least 8 characters."
            return
        }

        if (uiState.password != uiState.passwordConfirmation) {
            uiState.passwordConfirmationError = true
            uiState.errorMessage = "Passwords do not match."
            return
        }

        // Validate the new fields
        if (uiState.name.isEmpty()) {
            uiState.errorMessage = "Name is required."
            return
        }

        if (uiState.age.isEmpty()) {
            uiState.errorMessage = "Age is required."
            return
        }

        if (uiState.gender.isEmpty()) {
            uiState.errorMessage = "Gender is required."
            return
        }

        if (uiState.height.isEmpty()) {
            uiState.errorMessage = "Height is required."
            return
        }

        if (uiState.weight.isEmpty()) {
            uiState.errorMessage = "Weight is required."
            return
        }

        try {
            UserRepository.createUser(uiState.email, uiState.password, uiState.name, uiState.age, uiState.gender, uiState.height, uiState.weight)
        } catch (e: SignUpException) {
            uiState.errorMessage = e.message ?: "Something went wrong. Please try again."
        }
    }
}
