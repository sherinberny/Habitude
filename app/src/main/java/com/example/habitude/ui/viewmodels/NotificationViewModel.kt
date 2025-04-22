package com.example.habitude.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitude.ui.models.Notification
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NotificationViewModel : ViewModel() {

    // Define a MutableStateFlow to hold the notifications list
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    init {
        // Fetch notifications from Firestore when the ViewModel is created
        fetchNotifications()
    }

    // Function to fetch notifications from Firestore
    private fun fetchNotifications() {
        viewModelScope.launch {
            try {
                val db = FirebaseFirestore.getInstance()
                val notificationsList = db.collection("notifications")
                    .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get()
                    .await()

                // Map the documents to Notification objects
                val notifications = notificationsList.documents.mapNotNull { document ->
                    document.toObject(Notification::class.java)?.copy(id = document.id)
                }

                // Update the StateFlow with the new list of notifications
                _notifications.value = notifications
            } catch (e: Exception) {
                // Handle errors
                println("Error fetching notifications: $e")
            }
        }
    }
}
