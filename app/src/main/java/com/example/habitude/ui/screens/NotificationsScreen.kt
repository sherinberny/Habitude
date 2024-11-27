package com.example.habitude.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun NotificationsScreen(navController: NavController) {
    // Screen content for Notifications
    Column(modifier = androidx.compose.ui.Modifier.fillMaxHeight().padding(16.dp)) {
        Text("Notifications", modifier = androidx.compose.ui.Modifier.padding(16.dp))
        // Add your content and layout for the Profile screen
    }
}