package com.example.habitude.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.habitude.ui.navigation.Routes

@Composable
fun LaunchScreen(navHostController: NavHostController) {
    // Load the logo image resource from the drawable folder
    val logo: Painter = painterResource(id = com.example.habitude.R.drawable.habitude_name)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the logo at the top
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp) // Size of the circle
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape) // Set circular background color
                .padding(16.dp) // Padding between the logo and the circle
        ) {
            Image(
                painter = logo,
                contentDescription = "Habitude Logo",
                modifier = Modifier.size(120.dp) // Set size of the logo inside the circle
            )
        }

        // Text content below the logo

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = "Welcome to Habitude!",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "We hope this helps habit tracker helps you meet your goals!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Before you continue you will need to create an account",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navHostController.navigate(Routes.signUp.route) }) {
                Text(text = "Create Account")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account?")
                TextButton(onClick = { navHostController.navigate(Routes.signIn.route) }) {
                    Text(text = "Sign in")
                }
            }
        // Spacer to provide some space between the text and the button
        Spacer(modifier = Modifier.height(16.dp))

        }
    }
}
