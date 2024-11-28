package com.example.habitude.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.habitude.ui.components.FormField
import com.example.habitude.ui.navigation.Routes
import com.example.habitude.ui.repositories.UserRepository
import com.example.habitude.ui.viewmodels.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navHostController: NavHostController) {
    val viewModel: SignUpViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Surface(shadowElevation = 2.dp) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = "Create Account", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))

                // Existing fields...


                // New fields
                FormField(
                    value = state.name,
                    onValueChange = { state.name = it },
                    placeholder = { Text("Name") }
                )
                FormField(
                    value = state.age,
                    onValueChange = { state.age = it },
                    placeholder = { Text("Age") }
                )
                FormField(
                    value = state.gender,
                    onValueChange = { state.gender = it },
                    placeholder = { Text("Gender") }
                )
                FormField(
                    value = state.height,
                    onValueChange = { state.height = it },
                    placeholder = { Text("Height") }
                )
                FormField(
                    value = state.weight,
                    onValueChange = { state.weight = it },
                    placeholder = { Text("Weight") }
                )

                FormField(
                    value = state.email,
                    onValueChange = { state.email = it },
                    placeholder = { Text("Email") },
                    error = state.emailError
                )
                FormField(
                    value = state.emailConfirmation,
                    onValueChange = { state.emailConfirmation = it},
                    placeholder = { Text("Email Confirmation") },
                    error = state.emailConfirmationError
                )
                FormField(
                    value = state.password,
                    onValueChange = { state.password = it },
                    placeholder = { Text("Password") },
                    error = state.passwordError,
                    password = true
                )
                FormField(
                    value = state.passwordConfirmation,
                    onValueChange = { state.passwordConfirmation = it },
                    placeholder = { Text("Password Confirmation") },
                    error = state.passwordConfirmationError,
                    password = true
                )

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { navHostController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.signUp()
                                if (UserRepository.getCurrentUserId() != null) {
                                    navHostController.navigate(Routes.appNavigation.route) {
                                        popUpTo(navHostController.graph.id) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        },
                        elevation = null
                    ) {
                        Text(text = "Create Account")
                    }
                }

                Text(
                    text = state.errorMessage,
                    style = TextStyle(color = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}
