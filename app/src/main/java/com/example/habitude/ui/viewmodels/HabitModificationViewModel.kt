package com.example.habitude.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.habitude.ui.repositories.HabitRepository

class HabitModificationScreenState {
  var title by mutableStateOf("")
  var daily_duration by mutableStateOf("")
  var frequency by mutableStateOf("")
  var start_date by mutableStateOf("")
  var completed by mutableStateOf(false)
  var avoid by mutableStateOf(false)
  var titleError by mutableStateOf(false)
  var errorMessage by mutableStateOf("")
  var saveSuccess by mutableStateOf(false)
}

class HabitModificationViewModel(application: Application) : AndroidViewModel(application) {
  val uiState = HabitModificationScreenState()
  var id: String? = null

  suspend fun setUpInitialState(id: String?) {
    if (id == null || id == "new") return
    this.id = id
    val habit = HabitRepository.getHabits().find{it.id == id} ?: return
    uiState.title = habit.title ?: ""
    uiState.daily_duration = habit.daily_duration ?: ""
    uiState.frequency = habit.frequency ?: ""
    uiState.start_date = habit.start_date ?: ""
    uiState.completed = habit.completed ?: false
    uiState.avoid = habit.avoid ?: false
  }
  suspend fun saveHabit() {
    uiState.errorMessage = ""
    uiState.titleError = false

    if (uiState.title.isEmpty()) {
      uiState.titleError = true
      uiState.errorMessage = "Title cannot be blank"
      return
    }

    if (id == "someId") {
      HabitRepository.createHabit(uiState.title, uiState.start_date, uiState.frequency, uiState.daily_duration, uiState.avoid)
    } else {
      val habit = HabitRepository.getHabits().find { it.id == id }
      if (habit != null) {
        HabitRepository.updateHabit(
          //  habit.copy(title = uiState.title, completed = uiState.completed, avoid = uiState.avoid))
            habit.copy(title = uiState.title, start_date = uiState.start_date, frequency = uiState.frequency, daily_duration = uiState.daily_duration, avoid = uiState.avoid))
      }
    }
    uiState.saveSuccess = true
  }
}
