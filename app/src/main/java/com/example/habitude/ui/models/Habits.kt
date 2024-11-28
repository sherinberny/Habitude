package com.example.habitude.ui.models

data class Habits(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val start_date: String? = null,
    val daily_duration: String? = null,
    val frequency: String? = null,
    val completed: Boolean? = null,
    val avoid: Boolean? = null,
    var skippedDates: List<String> = emptyList(), // Calculated skipped dates
    var trackedDates: List<String> = emptyList(), // Calculated skipped dates
    var totalTracked: Int = 0 // Calculated tracked count
)
{}