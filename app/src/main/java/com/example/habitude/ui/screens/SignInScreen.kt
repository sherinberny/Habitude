package com.example.habitude.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.habitude.ui.components.FormField
import com.example.habitude.ui.navigation.Routes
import com.example.habitude.ui.repositories.UserRepository
import com.example.habitude.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navHostController: NavHostController) {
    val viewModel: SignInViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState

    // Scaffold to manage padding and other UI components
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo or App Title in the center of the screen
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp) // Size of the circle
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape) // Set circular background color
                    .padding(16.dp) // Padding between the logo and the circle
            ) {
                Image(
                    painter = painterResource(id = com.example.habitude.R.drawable.habitude_name),
                    contentDescription = "Habitude Logo",
                    modifier = Modifier.size(120.dp) // Set size of the logo inside the circle
                )
            }

            // Sign-In Form inside a Card to give a neat, elevated look
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors( // Set the background color
                    containerColor = Color(0xFF570C3E).copy(0.4f) // Light gray, replace with your desired color
                ),
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Form Fields for Email and Password
                    FormField(
                        value = state.email,
                        onValueChange = { state.email = it },
                        placeholder = { Text("Email") },
                        error = state.emailError,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    FormField(
                        value = state.password,
                        onValueChange = { state.password = it },
                        placeholder = { Text("Password") },
                        error = state.passwordError,
                        password = true
                    )

                    // Row for Cancel button and Sign In button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cancel Button
                        TextButton(
                            onClick = { navHostController.popBackStack() },
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text(
                                text = "Cancel",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Sign-In Button
                        Button(
                            onClick = {
                                scope.launch {
                                    viewModel.signIn()
                                    if (UserRepository.getCurrentUserId() != null) {
                                        navHostController.navigate(Routes.appNavigation.route) {
                                            popUpTo(navHostController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.width(120.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = "Sign in")
                        }
                    }

                    // Error Message
                    if (state.errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.errorMessage,
                            style = TextStyle(color = MaterialTheme.colorScheme.error),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
