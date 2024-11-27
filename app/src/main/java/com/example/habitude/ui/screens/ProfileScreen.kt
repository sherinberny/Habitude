package com.example.habitude.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier // Ensure this is imported
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*


import androidx.navigation.NavHostController
import com.example.habitude.ui.viewmodels.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileScreen(navHostController: NavHostController) {
    val profileViewModel: ProfileViewModel = viewModel()
    val userData = profileViewModel.userData.value

    Column(
        modifier = Modifier
            .fillMaxSize() // This fills the screen size in both width and height
            .padding(16.dp)
    ) {
        Text(text = "Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp)) // Spacer with 16dp height

        // Show loading indicator if user data is null (loading state)
        if (userData == null) {
            CircularProgressIndicator()
        } else {
            // Display user data
            Text("Name: ${userData.name}")
            Text("Age: ${userData.age}")
            Text("Gender: ${userData.gender}")
            Text("Height: ${userData.height}")
            Text("Weight: ${userData.weight}")
        }

        // You can also add functionality for updating profile data or logging out
        Spacer(modifier = Modifier.height(32.dp)) // Another Spacer with 32dp height

        Button(onClick = {
            // Handle Logout or Profile Edit here
            // UserRepository.logout() // Uncomment to enable logout
        }) {
            Text(text = "Logout")
        }
    }
}

