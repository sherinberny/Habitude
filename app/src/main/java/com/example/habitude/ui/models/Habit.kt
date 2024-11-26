package com.example.habitude.ui.models

data class Habit(
  val id: String? = null,
  val userId: String? = null,
  val title: String? = null,
  val start_date: String? = null,
  val daily_duration: String? = null,
  val frequency: String? = null,
  val completed: Boolean? = null,
  val avoid: Boolean? = null

) {
}


