package com.example.habitude.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.habitude.ui.navigation.Routes
import com.example.habitude.ui.repositories.UserRepository
import kotlinx.coroutines.*

@Composable
fun SplashScreen(navHostController: NavHostController) {

    LaunchedEffect(true) {
        // Simulate a login status check (add your logic inside this block)
        delay(1000) // Wait for 1 second before performing the check (simulating loading)

        // Perform the login check
        val isLoggedIn = UserRepository.getCurrentUserId() != null

        // Navigate based on whether the user is authenticated or not
        navHostController.navigate(
            if (isLoggedIn) Routes.appNavigation.route else Routes.launchNavigation.route
        ) {
            // Make sure to pop the splash screen off the back stack
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    // UI layout of the splash screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Habitude", // App name
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "SRS", // Subtitle or slogan
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
    }
}
