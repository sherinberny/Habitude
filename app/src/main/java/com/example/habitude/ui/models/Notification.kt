package com.example.habitude.ui.models

data class Notification(
    val id: String,
    val title: String,
    val msg: String,
    val timestamp: Long,
    val userId: String // If you want to associate the notification with a user
)
