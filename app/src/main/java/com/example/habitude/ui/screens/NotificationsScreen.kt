package com.example.habitude.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.habitude.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    val notifications = remember { mutableStateOf<List<NotificationData>>(emptyList()) }

    // Fetch notifications from Firestore
    LaunchedEffect(true) {
        val db = FirebaseFirestore.getInstance()
        db.collection("notifications")
            .orderBy("timestamp")  // Ordering by timestamp
            .get()
            .addOnSuccessListener { result ->
                val data = result.documents.mapNotNull { it.toObject<NotificationData>() }
                notifications.value = data
            }
            .addOnFailureListener { e ->
                println("Error getting notifications: $e")
            }
    }

    Scaffold(topBar = {
    }) { paddingValues ->
        if (notifications.value.isEmpty()) {
            // Show a message if there are no notifications
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No notifications available")
            }
        } else {
            // Display notifications
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(notifications.value) { notification ->
                    NotificationItem(notification)
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors( // Set the background color
            containerColor = Color(0xFF570C3E).copy(0.4f) // Light gray, replace with your desired color
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Notification Icon (Info icon)
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info Icon",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))

                // Notification Title
                Text(
                    text = notification.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Notification Body
            Text(
                text = notification.msg ?: "No Message",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Divider
            Divider(modifier = Modifier.fillMaxWidth())

            // Timestamp (display in a small font)
            Text(
                text = "Received at: ${notification.timestamp?.let { formatTimestamp(it) } ?: "Unknown time"}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black.copy(alpha = 0.5f)
            )
        }
    }
}

// Helper function to format timestamp (milliseconds)
fun formatTimestamp(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val format = java.text.SimpleDateFormat("dd MMM yyyy HH:mm:ss", java.util.Locale.getDefault())
    return format.format(date)
}

@Preview
@Composable
fun PreviewNotificationItem() {
    val notification = NotificationData(
        title = "New Update Available",
        msg = "A new update for the app is now available. Please update to the latest version.",
        timestamp = System.currentTimeMillis()
    )
    NotificationItem(notification = notification)
}

data class NotificationData(
    val id: String? = null,
    val title: String? = null,
    val msg: String? = null,
    val timestamp: Long? = null
)
