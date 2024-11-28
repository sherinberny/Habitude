package com.example.habitude.ui.repositories

import com.example.habitude.ui.models.Habit
import com.example.habitude.ui.models.Notification
import com.example.habitude.ui.repositories.HabitRepository.habitCache
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object NotificationRepository {

    suspend fun createNotification(title: String, body: String) {
        val doc = Firebase.firestore.collection("notifications").document()
        val notification = Notification(
            id = doc.id,
            title = title,
            msg = body,
            timestamp = System.currentTimeMillis(),
            userId = UserRepository.getCurrentUserId().toString() // Make sure to pass the current user id if necessary
        )
        doc.set(notification).await()
    }
}