package com.example.habitude.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.habitude.ui.navigation.Routes
import com.example.habitude.ui.repositories.UserRepository
import kotlinx.coroutines.*

@Composable
fun SplashScreen(navHostController: NavHostController) {
    // Load the logo image resource from the drawable folder
    val logo: Painter = painterResource(id = com.example.habitude.R.drawable.habitude_name)

    // Start the effect when the SplashScreen is displayed
    LaunchedEffect(true) {
        // Simulate a loading state (like checking user authentication status)
        delay(1000) // Wait for 1 second before performing the check

        // Perform the login check
        val isLoggedIn = UserRepository.getCurrentUserId() != null

        // Navigate based on the user's authentication status
        navHostController.navigate(
            if (isLoggedIn) Routes.appNavigation.route else Routes.launchNavigation.route
        ) {
            // Make sure to pop the splash screen off the back stack
            popUpTo(navHostController.graph.findStartDestination().id) {
                inclusive = true
            }
        }
    }

    // UI layout of the splash screen with a background color and centered logo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary) // Set the background color here
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display the logo in the center
            Image(
                painter = logo,
                contentDescription = "App Logo",
                modifier = Modifier.size(200.dp) // Set the logo size
            )

        }
    }
}
